package com.zhangbin.concurrency.pattern;

import java.util.Vector;
import java.util.concurrent.Semaphore;

/**
 * @author zhangbin
 * @Type SemaphoreProducerConsumerPattern
 * @Desc
 * @date 2019-05-22
 * @Version V1.0
 */
public class SemaphoreProducerConsumerPattern {

    public static void main(String[] args) {

        Semaphore producer = new Semaphore(5);

        Semaphore consumer = new Semaphore(0);


        Vector<String> shareQueue = new Vector<>();


        Thread p1 = new Thread(() -> {

            for (int i = 0; i < 10; i++) {

                try {
                    producer.acquire();

                    String value = Thread.currentThread().getName() + "_" + i;
                    System.out.println(Thread.currentThread().getName() + " 生产了 " + value);
                    shareQueue.add(value);

                    consumer.release();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }

        }, "producer1");




        Thread c1 = new Thread(() -> {
            for (; ; ) {

                try {
                    consumer.acquire();

                    String value = shareQueue.remove(0);

                    System.out.println(Thread.currentThread().getName() + " 消费了 " + value);

                    producer.release();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }, "consumer1");


        p1.start();
        c1.start();


    }
}
