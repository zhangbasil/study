package com.zhangbin.concurrency.thread;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author zhangbin
 * @Type ThreadCreationQuestion
 * @Desc 创建线程的方式
 * @date 2019-04-16
 * @Version V1.0
 */
public class ThreadCreationQuestion {

    public static void main(String[] args) {

        Optional<String> optional = Stream.of(1, 2, 3, 4, 5).filter(item -> Objects.equals(11, item)).findAny().map(item -> "sbc");

        System.out.println("optional = " + optional);


    }

    private static class MyThread extends Thread {

        @Override
        public void run() {
            System.out.println("true = " + true);
        }
    }

}
