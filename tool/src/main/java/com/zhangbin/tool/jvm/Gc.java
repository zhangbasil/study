package com.zhangbin.tool.jvm;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
public class Gc {


    private static ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(50);


    public static void main(String[] args) {

        for (; ; ) {
            modelFit();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }


    }


    private static void modelFit() {
        getAllCardInfo().forEach(info -> executor.schedule(info::m, 2, TimeUnit.SECONDS));
    }


    private static List<CardInfo> getAllCardInfo() {
        List<CardInfo> cardInfos = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            cardInfos.add(new CardInfo());
        }
        return cardInfos;
    }


    static class CardInfo {

        BigDecimal price = new BigDecimal("0.0");

        String name = "张三";

        int age = 12;

        Date birthday = new Date();


        public void m() {

        }


    }


}
