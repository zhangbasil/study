package com.zhangbin.tool.excel;

import com.zhangbin.tool.excel.annotatoin.ExcelField;
import com.zhangbin.tool.excel.annotatoin.Style;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
@Slf4j
public final class ExcelWriter {

    public static <T> void write(String filePath, List<T> data) {
        Workbook workbook = createWorkbook(data);
        try (OutputStream outputStream = new FileOutputStream(filePath)) {
            workbook.write(outputStream);
            outputStream.flush();
        } catch (IOException e) {
            log.error("写入文件流异常：{}", e.getMessage(), e);
        }
    }

    public static <T> void write(String fileName, List<T> data, HttpServletResponse response) {
        Workbook workbook = createWorkbook(data);
        OutputStream outputStream = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));
            outputStream = response.getOutputStream();
            workbook.write(outputStream);
            outputStream.flush();
        } catch (IOException e) {
            log.error("写文件流异常：{}", e.getMessage(), e);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    log.error("关闭output异常：{}", e.getMessage(), e);
                }
            }
        }

    }

    private static <T> Workbook createWorkbook(List<T> data) {
        Workbook workbook = new HSSFWorkbook();
        if (Objects.isNull(data) || data.size() == 0) {
            return workbook;
        }

        T t = data.get(0);
        Class<?> clazz = t.getClass();

        int count = data.size();
        int size = 50000;
        int sheets = (int) Math.ceil((double) count / size);

        List<Field> fields = Stream.of(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(ExcelField.class)).collect(Collectors.toList());

        for (int i = 1; i <= sheets; i++) {
            List<T> sheetData = new ArrayList<>();
            int offset = (i - 1) * size;
            for (int j = offset; j < offset + size; j++) {
                if (j >= data.size()) {
                    break;
                }
                sheetData.add(data.get(j));
            }
            Sheet sheet = workbook.createSheet("sheet-" + i);
            createHead(workbook, sheet, fields);
            createBody(workbook, sheet, fields, sheetData);
        }
        return workbook;
    }

    private static <T> void createBody(Workbook workbook, Sheet sheet, List<Field> fields, List<T> data) {
        CellStyle cellStyle = createBodyStyle(workbook);
        for (int i = 0; i < data.size(); i++) {
            Row row = sheet.createRow((i + 1));
            row.setHeightInPoints(23);
            for (int j = 0; j < fields.size(); j++) {
                Field field = fields.get(j);
                field.setAccessible(true);
                Object value;
                try {
                    value = field.get(data.get(i));
                } catch (IllegalAccessException e) {
                    log.error("反射获取值异常：{}", e.getMessage(), e);
                    value = "数据异常";
                }
                if (value instanceof Date) {
                    ExcelField excelField = field.getAnnotation(ExcelField.class);
                    String pattern = excelField.datePattern();
                    // TODO
//                    value = DateUtils.getFormatDate((Date) value, pattern);
                }
                CellUtil.createCell(row, j, value == null ? "" : value.toString(), cellStyle);
            }
        }
    }

    private static void createHead(Workbook workbook, Sheet sheet, List<Field> fields) {
        Row row = sheet.createRow(0);
        row.setHeightInPoints(27);
        for (int i = 0; i < fields.size(); i++) {
            Field field = fields.get(i);
            ExcelField excelField = field.getAnnotation(ExcelField.class);
            Style style = excelField.style();
            int width = style.width();
            sheet.setColumnWidth(i, width * 256);
            CellStyle cellStyle = createHeadStyle(workbook);
            CellUtil.createCell(row, i, excelField.headName(), cellStyle);
        }
    }

    private static CellStyle createHeadStyle(Workbook workbook) {
        Font font = workbook.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setColor(HSSFColor.BLUE.index);
        font.setFontHeightInPoints((short) 10);
        CellStyle cellStyle = createCellStyle(workbook, font);
        cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        return cellStyle;
    }

    private static CellStyle createBodyStyle(Workbook workbook) {
        Font font = workbook.createFont();
        font.setColor(HSSFColor.BLACK.index);
        font.setFontHeightInPoints((short) 10);
        return createCellStyle(workbook, font);
    }

    private static CellStyle createCellStyle(Workbook workbook, Font font) {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        return cellStyle;
    }
}
