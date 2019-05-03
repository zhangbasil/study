package com.zhangbin.concurrency.sync;

/**
 * @author zhangbin
 * @Type VolatileQuestion
 * @Desc
 *
 *
 * Volatile 特点： 可见性  禁止指令重排序
 *
 * 原理：内存屏障指令
 *
 * @date 2019-04-26
 * @Version V1.0
 */
public class VolatileQuestion {


    private volatile int number;


    private static synchronized void sync() {

        System.out.println(" hello world ");

    }

    public static void main(String[] args) {

        sync();

    }


}
