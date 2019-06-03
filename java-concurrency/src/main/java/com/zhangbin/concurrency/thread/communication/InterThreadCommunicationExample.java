package com.zhangbin.concurrency.thread.communication;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author zhangbin
 * @Type InterThreadCommunicationExample
 * @Desc 线程间通信
 * <p>
 * <p>
 * 启动两个线程, 一个输出 1,3,5,7…99, 另一个输出 2,4,6,8…100 最后 STDOUT 中按序输出 1,2,3,4,5…100
 * @date 2019-05-18
 * @Version V1.0
 */
public class InterThreadCommunicationExample {

    public static void main(String[] args) throws InterruptedException {
        new InterThreadCommunicationExample().threadQuestion();
//        threadSort();

    }


    /**
     * 启动两个线程, 一个输出 1,3,5,7…99, 另一个输出 2,4,6,8…100 最后 STDOUT 中按序输出 1,2,3,4,5…100
     */
    private void threadQuestion() throws InterruptedException {

        Thread t1 = new Thread(() -> {

            for (int i = 1; i <= 100; i++) {

                synchronized (this) {

                    if (i % 2 != 0) {
                        System.out.println("t1 i = " + i + " 进入等待");

                        try {
                            this.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        this.notify();
                    }

                }


            }
        });

        Thread t2 = new Thread(() -> {

            for (int i = 1; i <= 100; i++) {

                synchronized (this) {
                    if (i % 2 == 0) {
                        System.out.println("t2 i = " + i + " 进入等待");
                        try {
                            this.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        this.notify();
                    }
                }

            }
        });

        t1.start();
        t2.start();
    }

    private static void threadSort() throws InterruptedException {
        Thread t1 = new Thread(() -> System.out.println(" t1 "), "t1");
        Thread t2 = new Thread(() -> System.out.println(" t2 "), "t2");
        Thread t3 = new Thread(() -> System.out.println(" t3 "), "t3");

        t1.start();
        t1.join();
        t2.start();
        t2.join();
        t3.start();



    }


    private static void producerConsumer() {
        Queue<Integer> shareQ = new LinkedList<>();

        Thread producer = new Thread(() -> {
            for (int i = 1; i <= 100; i++) {
                synchronized (shareQ) {
                    if (shareQ.size() > 0) {
                        while (true) {
                            try {
                                shareQ.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                    System.out.println(Thread.currentThread().getName() + " producers " + i);
                    shareQ.add(i);
                    shareQ.notify();

                }
            }

        }, "producer");


        Thread consumer = new Thread(() -> {
            while (true) {
                synchronized (shareQ) {

                    if (shareQ.size() > 0) {
                        Integer number = shareQ.poll();
                        System.out.println(Thread.currentThread().getName() + " consumers " + number);
                        shareQ.notifyAll();
                    }

                    try {
                        shareQ.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

            }

        }, "consumer");

        producer.start();
        consumer.start();

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(" ============ ");

    }

    private void producerConsumerPatern() {
        Queue<Integer> shareQ = new LinkedList<>();
        Producer producer = new Producer(shareQ);
        Consumer consumer = new Consumer(shareQ);
        producer.start();
        consumer.start();
    }


}
