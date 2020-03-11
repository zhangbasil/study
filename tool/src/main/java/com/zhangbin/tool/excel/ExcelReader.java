package com.zhangbin.tool.excel;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
@Slf4j
public final class ExcelReader {
    public static List<String[]> read(String filePath, int startIndex) {
        Workbook workbook = getWorkbook(filePath);
        return buildData(workbook, startIndex);
    }

    public static List<String[]> read(InputStream inputStream, int startIndex) {
        Workbook workbook = getWorkbook(inputStream);
        return buildData(workbook, startIndex);
    }

    private static List<String[]> buildData(Workbook workbook, int startIndex) {
        List<String[]> data = new ArrayList<>();
        Sheet sheet = workbook.getSheetAt(0);
        int rows = sheet.getPhysicalNumberOfRows();
        for (int i = startIndex; i < rows; i++) {
            Row row = sheet.getRow(i);
            if (Objects.isNull(row)) {
                continue;
            }
            try {
//                int cells = row.getPhysicalNumberOfCells();
                int len = row.getLastCellNum();
                String[] strings = new String[len];
                for (int j = 0; j < len; j++) {
                    Cell cell = row.getCell(j);
                    if (Objects.isNull(cell)) {
                        strings[j] = null;
                        continue;
                    }
                    int cellType = cell.getCellType();
                    Object cellValue;
                    switch (cellType) {
                        case Cell.CELL_TYPE_NUMERIC:
                            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                Date dateCellValue = cell.getDateCellValue();
                                cellValue = dateCellValue.getTime() + "";
                            } else {
                                BigDecimal bigDecimal = BigDecimal.valueOf(cell.getNumericCellValue());
                                cellValue = bigDecimal.toBigInteger().toString();
                            }
                            break;
                        case Cell.CELL_TYPE_BOOLEAN:
                            boolean booleanCellValue = cell.getBooleanCellValue();
                            cellValue = booleanCellValue ? "true" : "false";
                            break;
                        default:
                            cellValue = cell.getStringCellValue();
                            if (cellValue.equals("yyyyyyyy")) {
                                cellValue = "true";
                            } else if (cellValue.equals("zzzzzzzz")){
                                cellValue = "false";
                            }
                    }
                    strings[j] = cellValue.toString();
                }
                data.add(strings);
            } catch (Exception e) {
                log.error("通过反射获取值异常：{}", e.getMessage(), e);
            }

        }
        return data;
    }


    private static Workbook getWorkbook(String filePath) {
        Workbook workbook = null;
        try (InputStream inputStream = new FileInputStream(filePath)) {
            workbook = WorkbookFactory.create(inputStream);
        } catch (Exception e) {
            log.error("创建Workbook异常：{}", e.getMessage(), e);
        }
        return workbook;
    }

    private static Workbook getWorkbook(InputStream inputStream) {
        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(inputStream);
        } catch (IOException | InvalidFormatException e) {
            log.error("创建Workbook异常：{}", e.getMessage(), e);
        }
        return workbook;
    }
}
