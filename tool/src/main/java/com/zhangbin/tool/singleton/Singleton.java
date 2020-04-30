package com.zhangbin.tool.singleton;

import java.util.Objects;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
public class Singleton {

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
