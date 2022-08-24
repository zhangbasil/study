package com.zhangbin.tool.http;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
public class HttpUtils {

    private final static OkHttpClient okHttpClient = new OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS).build();


    public static void main(String[] args) throws Exception {
//        stock();

        fangZi();

    }


    private static void fangZi() throws IOException {
        String url = "http://www.fangdi.com.cn/old_house/old_house_list.html?district=c3a04b33d3238385&area=&location=&listingNo=&region=acba04997bf54f84&blockName=&verifyNo=&time=c3243f2018413f06&houseType=27d3af3bd45acf5e&check_input=lnz3";


        Request request = new Request.Builder()
//                .addHeader("Cookie", "device_id=1cc99d4a3c814a04275986fcb62f24ee; s=dq12mmbwnc; __utmz=1.1635991750.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); bid=bb96f3eecf7200bf663e9bc20e336635_kwn3gmaa; __utmc=1; __utma=1.407667850.1635991750.1646270471.1646637994.10; snbim_minify=true; Hm_lvt_1db88642e346389874251b5a1eded6e3=1654492318; xq_a_token=caef71bb7884dfbef0e3e1d44b7d1385790826a2; xqat=caef71bb7884dfbef0e3e1d44b7d1385790826a2; xq_id_token=eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJ1aWQiOjg4OTU3MTMwOTAsImlzcyI6InVjIiwiZXhwIjoxNjU3OTQ4NTQ4LCJjdG0iOjE2NTUzNTY1NDg4OTAsImNpZCI6ImQ5ZDBuNEFadXAifQ.eKSGamH2ch47XE5T9oGxDT1vD0BPwgmRbfxGYZAIKIlagVlMsafsEXcW5K_EEr2WKUGjg7hML-emWFbYXyTJ6roctPoF3lxpz9owzmz-PvXJ-wgsmYlezEM7w4Mtm-6Y1iGgaugYi5f_ApAV0jTdHX6Xk-GRWRjf-ia6H9G1-9WQgzS_JndyYmdfAnrma5JNDiaB7goswLq1m8WQNBfx2q_iUa0b3G-DvQg3JCZkU_8pDs2HgRvZH5y9PWPR86vxAecZwfyu8aRGW6JXMhD9L-u7WNYQ-c8jzFDrQEIfjWlY1MWrKN6q5KmpIJQkqIqXZytv-jizkIm2EGngfkaM_A; xq_r_token=9e6c33eade66ed2d8c36bcac37da0c60c251b128; xq_is_login=1; u=8895713090; acw_tc=2760827316563064260202502ebd84ebbcba24ac1fdc3840ae43b2e00ac785; is_overseas=0; Hm_lpvt_1db88642e346389874251b5a1eded6e3=1656306603")
                .url(url).get().build();

        Call call = okHttpClient.newCall(request);

        Response response = call.execute();
        assert response.body() != null;
        String string = response.body().string();

        System.out.println("string = " + string);
    }


    private static void stock() {
        String url = "https://xueqiu.com/v4/statuses/user_timeline.json?page=1&user_id=8148827042";

        Request request = new Request.Builder()
                .addHeader("Cookie", "device_id=1cc99d4a3c814a04275986fcb62f24ee; s=dq12mmbwnc; __utmz=1.1635991750.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); bid=bb96f3eecf7200bf663e9bc20e336635_kwn3gmaa; __utmc=1; __utma=1.407667850.1635991750.1646270471.1646637994.10; snbim_minify=true; Hm_lvt_1db88642e346389874251b5a1eded6e3=1654492318; xq_a_token=caef71bb7884dfbef0e3e1d44b7d1385790826a2; xqat=caef71bb7884dfbef0e3e1d44b7d1385790826a2; xq_id_token=eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJ1aWQiOjg4OTU3MTMwOTAsImlzcyI6InVjIiwiZXhwIjoxNjU3OTQ4NTQ4LCJjdG0iOjE2NTUzNTY1NDg4OTAsImNpZCI6ImQ5ZDBuNEFadXAifQ.eKSGamH2ch47XE5T9oGxDT1vD0BPwgmRbfxGYZAIKIlagVlMsafsEXcW5K_EEr2WKUGjg7hML-emWFbYXyTJ6roctPoF3lxpz9owzmz-PvXJ-wgsmYlezEM7w4Mtm-6Y1iGgaugYi5f_ApAV0jTdHX6Xk-GRWRjf-ia6H9G1-9WQgzS_JndyYmdfAnrma5JNDiaB7goswLq1m8WQNBfx2q_iUa0b3G-DvQg3JCZkU_8pDs2HgRvZH5y9PWPR86vxAecZwfyu8aRGW6JXMhD9L-u7WNYQ-c8jzFDrQEIfjWlY1MWrKN6q5KmpIJQkqIqXZytv-jizkIm2EGngfkaM_A; xq_r_token=9e6c33eade66ed2d8c36bcac37da0c60c251b128; xq_is_login=1; u=8895713090; acw_tc=2760827316563064260202502ebd84ebbcba24ac1fdc3840ae43b2e00ac785; is_overseas=0; Hm_lpvt_1db88642e346389874251b5a1eded6e3=1656306603")
                .url(url).get().build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            assert response.body() != null;
            String string = response.body().string();
            Result result = JSON.parseObject(string, Result.class);
            result.statuses.forEach(item -> {
                String description = item.text;
                Long createdAt = item.created_at;
                System.out.println(LocalDateTime.ofInstant(Instant.ofEpochMilli(createdAt), ZoneOffset.of("+8")) +
                        "       " + description);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Data
    static class Result {
        List<Statuses> statuses;
    }

    @Data
    static class Statuses {
        String text;

        Long created_at;
    }


}
