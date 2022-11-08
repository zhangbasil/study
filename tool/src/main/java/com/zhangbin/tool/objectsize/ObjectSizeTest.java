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

        int[] ids = {95974,
                97202,
                96873,
                96835,
                96204,
                96028,
                96760,
                96233,
                96027,
                96030,
                96232,
                96761,
                97140,
                95957,
                96224,
                96170,
                99711,
                97125,
                99715,
                96355,
                96255,
                96264,
                97884,
                97197,
                97550,
                96390,
                97549,
                96350,
                96875,
                96871,
                96383,
                96426,
                96250,
                96231,
                99691,
                97076,
                96205};

        for (int id : ids) {
            String url = "https://tsp-api.maxima-cars.com/api/web-core/depot/directly/" + id;
            send(url);

        }


    }


    private static void send(String url) {
        Request request = new Request.Builder()
                .addHeader("token", "49d5d3d76561c71129be59fee09ceeed")
                .url(url)
                .get()
                .build();
        try {
            Response response = HTTP_CLIENT.newCall(request).execute();
            System.out.println(url + "     response = " + response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
