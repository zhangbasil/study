package com.zhangbin.concurrency.pattern;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author zhangbin
 * @Type WaitNotifyProducerConsumerPattern
 * <p>
 * <p>
 * Java program to solve Producer Consumer problem using wait and notify
 * method in Java. Producer Consumer is also a popular concurrency design pattern.
 * @Desc
 * @date 2019-05-22
 * @Version V1.0
 */
public class WaitNotifyProducerConsumerPattern {

    public static void main(String[] args) {

//        Queue<Integer> shareQueue = new LinkedBlockingQueue<>();

        Vector<String> shareQueue = new Vector<>();


        Thread p1 = new Thread(new Producer(shareQueue, 10), "Producer1");
        Thread p2 = new Thread(new Producer(shareQueue, 10), "Producer2");
        Thread p3 = new Thread(new Producer(shareQueue, 10), "Producer3");

        Thread c1 = new Thread(new Consumer(shareQueue, 10), "Consumer1");
        Thread c2 = new Thread(new Consumer(shareQueue, 10), "Consumer2");
        Thread c3 = new Thread(new Consumer(shareQueue, 10), "Consumer3");
        Thread c4 = new Thread(new Consumer(shareQueue, 10), "Consumer4");
        Thread c5 = new Thread(new Consumer(shareQueue, 10), "Consumer5");

        p1.start();
        p2.start();
        p3.start();
        c1.start();
        c2.start();
        c3.start();
        c4.start();
        c5.start();


        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(" ============ ");


    }


}

class Producer implements Runnable {

    //    private final Queue<Integer> shareQueue;
    private final Vector<String> shareQueue;

    private int size;

    //    public Producer(Queue<Integer> shareQueue, int size) {
    public Producer(Vector<String> shareQueue, int size) {
        this.shareQueue = shareQueue;
        this.size = size;
    }

    @Override
    public void run() {

        // 生产 10 个任务
        for (int i = 0; i < 20; i++) {

            synchronized (shareQueue) {

                while (size == shareQueue.size()) {

                    System.out.println("ShareQueue is full " + Thread.currentThread().getName()
                            + " is waiting , size :" + shareQueue.size());

                    try {
                        shareQueue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


                // 把生产的任务放入共享队列中
                String value = Thread.currentThread().getName() + "_" + i;
                shareQueue.add(value);

                System.out.println(Thread.currentThread().getName() + " 生产了 " + value);
                // 通知其他线程（消费者）
                shareQueue.notifyAll();

            }


        }


    }
}

class Consumer implements Runnable {

    //    private final Queue<Integer> shareQueue;
    private final Vector<String> shareQueue;

    private int size;

    //    public Consumer(Queue<Integer> shareQueue, int size) {
    public Consumer(Vector<String> shareQueue, int size) {
        this.shareQueue = shareQueue;
        this.size = size;
    }

    @Override
    public void run() {

        for (; ; ) {

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (shareQueue) {
                while (shareQueue.isEmpty()) {
                    System.out.println("ShareQueue is empty " + Thread.currentThread().getName()
                            + " is waiting , size :" + shareQueue.size());
                    try {
                        shareQueue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                String value = shareQueue.remove(0);
                shareQueue.notifyAll();
                System.out.println(Thread.currentThread().getName() + " 消费了 " + value);
            }

        }

    }


}





