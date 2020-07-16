package com.zhangbin.tool.singleton;

import java.util.Objects;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
public class Singleton {


    public static void main(String[] args) {
        String url = "http://maxima-private-prod.oss-cn-hangzhou.aliyuncs.com/2020061000000309.jpg";


        String s = url.split("\\?")[0];
        System.out.println("s = " + s);


    }

    /**
     *  使用 volatile 是禁止指令重排序
     */
    private static volatile Singleton singleton;

    private Singleton() {

    }

    public static Singleton getInstance() {
        if (Objects.isNull(singleton)) {
            synchronized (Singleton.class) {
                if (Objects.isNull(singleton)) {
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }
}
