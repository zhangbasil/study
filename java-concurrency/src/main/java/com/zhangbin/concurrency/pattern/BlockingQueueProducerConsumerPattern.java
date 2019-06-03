package com.zhangbin.concurrency.pattern;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author zhangbin
 * @Type BlockingQueueProducerConsumerPattern
 * @Desc
 * @date 2019-05-22
 * @Version V1.0
 */
public class BlockingQueueProducerConsumerPattern {

    public static void main(String[] args) {

        BlockingQueue<String> shareQueue = new LinkedBlockingQueue<>();

        Thread p1 = new Thread(() -> {

            for (int i = 0; i < 10; i++) {

                try {
                    String value = Thread.currentThread().getName() + "_" + i;
                    System.out.println(Thread.currentThread().getName() + " 生产了 " + value);
                    shareQueue.put(value);

                    Thread.sleep(200);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }


        }, "producer1");


        Thread c1 = new Thread(() -> {

            for (; ; ) {
                try {
                    String value = shareQueue.take();
                    System.out.println(Thread.currentThread().getName() + " 消费了 " + value);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }, "consumer1");

        Thread c2 = new Thread(() -> {

            for (; ; ) {
                try {
                    String value = shareQueue.take();
                    System.out.println(Thread.currentThread().getName() + " 消费了 " + value);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }, "consumer2");

        p1.start();

        c1.start();
        c2.start();



    }


}
