package com.zhangbin.tool.objectsize;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
public class ObjectSizeTest {

    public static final OkHttpClient HTTP_CLIENT = new OkHttpClient.Builder()
            .connectTimeout(500, TimeUnit.MILLISECONDS)            //设置连接超时
            .readTimeout(500, TimeUnit.MILLISECONDS)                //设置读超时
            .writeTimeout(500, TimeUnit.MILLISECONDS)               //设置写超时
            .retryOnConnectionFailure(true)                         //是否自动重连
            .build();

    public static void main(String[] args) {

        for (int i = 200; i < 500; i++) {
            sendRequest(i);
        }


    }

    private static void sendRequest(int id) {

        String url = "http://192.168.2." + id + ":8088/";

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try {
            Response response = HTTP_CLIENT.newCall(request).execute();
            System.out.println("response = " + response);
        } catch (IOException e) {
            System.out.println(url + "  不对");
        }
    }
}
