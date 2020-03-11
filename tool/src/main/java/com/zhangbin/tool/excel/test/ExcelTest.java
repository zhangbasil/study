package com.zhangbin.tool.excel.test;

import com.alibaba.fastjson.JSON;
import com.zhangbin.tool.excel.ExcelReader;
import com.zhangbin.tool.excel.ExcelWriter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
public class ExcelTest {

    public static final OkHttpClient HTTP_CLIENT = new OkHttpClient.Builder()
            .connectTimeout(1000, TimeUnit.MILLISECONDS)            //设置连接超时
            .readTimeout(500, TimeUnit.MILLISECONDS)                //设置读超时
            .writeTimeout(500, TimeUnit.MILLISECONDS)               //设置写超时
            .retryOnConnectionFailure(true)                         //是否自动重连
            .build();


    public static void main(String[] args) {
        List<String[]> data = ExcelReader.read("/Users/admin/Documents/Untitled.xlsx", 1);
//        List<String[]> data = ExcelReader.read("/Users/admin/Documents/demo.xlsx", 1);


        List<User> users = data.parallelStream().map(item -> {
            String id = item[0];
            String custName = item[1];
            String mobile = item[2];
            String idNumber = item[3];
            String nickName = item[4];
            String headImg = item[5];

//            id_upside	id_inverse	driver_license_level	driver_upside	driver_inverse	gmt_create	introduction_name	introduction_mobile

            String id_upside = item[6];
            String id_inverse = item[7];
            String driver_license_level = item[8];
            String driver_upside = item[9];
            String driver_inverse = item[10];
            String gmt_create = item[11];
            String introduction_name = item[12];
            String introduction_mobile = item[13];




            String sharerId = item[14];
            String sharerMobile = item[15];
            String sharerName = item[16];
            String hasContract = item[17];
            if (!StringUtils.isEmpty(headImg) && headImg.length() < 30) {
                System.out.println("headImg = " + headImg);
                headImg = null;
            }

            return User.builder()
                    .idUpside(id_upside)
                    .idInverse(id_inverse)
                    .driverUpside(driver_upside)
                    .driverLicenseLevel(driver_license_level)
                    .driverInverse(driver_inverse)
                    .gmtCreate(gmt_create)
                    .introductionName(introduction_name)
                    .introductionMobile(introduction_mobile)
                    .customerId(id)
                    .idNumber(idNumber)
                    .realName(custName)
                    .nickName(nickName)
                    .mobile(mobile)
                    .headImg(headImg)
                    .shareName(sharerName)
                    .shareMobile(sharerMobile)
                    .hasContract(hasContract)
                    .shareCode(ShareCodeUtils.idToCode(Long.parseLong(id)))
                    .build();

        }).collect(Collectors.toList());

        ExcelWriter.write("/Users/admin/Documents/data-user0311-a.xlsx", users);

    }


    private static User getOneDate(Long userId) {
        String requestUrl = "https://st-wx-api.maxima-cars.com/app/admin/info?admin=admin&userId=" + userId;
        Request request = new Request.Builder()
                .url(requestUrl)
                .get()
                .build();
        try {
            Response response = HTTP_CLIENT.newCall(request).execute();
            if (!response.isSuccessful()) {
                System.out.println("response = " + response);
            }
            String result = Objects.requireNonNull(response.body()).string();
            if (StringUtils.isEmpty(request)) {
                return null;
            }
            return JSON.parseObject(result, User.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private static void exec() {
        List<User> users = new ArrayList<>();
        for (int i = 1; i < 80000; i++) {
            User oneDate = getOneDate((long) i);
            if (oneDate != null) {
                users.add(oneDate);
            }
        }
        ExcelWriter.write("/Users/admin/Documents/data.xlsx", users);
    }


}
