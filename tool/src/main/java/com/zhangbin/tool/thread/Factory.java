package com.zhangbin.tool.thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 多线程通讯  wait notify notifyAll
 * 循环打印
 *
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
public class Factory {

    private final int[] items = new int[1];

    private int size = 0;


    // 生产数据
    public synchronized void produce() {
        // 循环生成数据
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + "  开始生成数据...");
            if (size == items.length) {
                System.out.println("数据生成完成" + Thread.currentThread().getName() + "  线程进入阻塞");
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "线程被唤醒");
            }
            items[0] = 100;
            size++;
            // 生产了数据，通知消费数据
            notify();
        }
    }


    // 消费者
    public synchronized void consumer() {
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + "  开始消费数据...");
            // 数据消费完成，通知生产者生产数据,
            if (size == 0) {
                //
                System.out.println("数据消费完成" + Thread.currentThread().getName() + "  线程进入阻塞");
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "线程被唤醒");
            }
            size--;
            notify();
        }

    }


    public static void main(String[] args) {
        Factory factory = new Factory();

//        Thread t1 = new Thread(factory::produce, "t1->生产者");
//        Thread t2 = new Thread(factory::consumer, "t2->消费者");


//        Thread t1 = new Thread(factory::printNumber, "t1");
//        Thread t2 = new Thread(factory::printChar, "t2");

        Thread t1 = new Thread(factory::print, "t1");
        Thread t2 = new Thread(factory::print, "t2");
        t1.start();
        t2.start();


    }


    public synchronized void printNumber() {
        for (int i = 0; i < 10; i++) {
            // 偶数
            if (i % 2 == 0) {
                System.out.println(Thread.currentThread().getName() + " --> " + i);
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            notifyAll();
        }
    }

    public synchronized void printChar() {
        for (int i = 0; i < 10; i++) {
            // 奇数
            if (i % 2 != 0) {
                System.out.println(Thread.currentThread().getName() + "  " + i);
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            notifyAll();
        }
    }


    final Object obj = new Object();

    AtomicInteger num = new AtomicInteger(0);

    public void print() {
        synchronized (obj) {
            for (int i = 0; i < 10; i++) {

            }
        }


    }


}
