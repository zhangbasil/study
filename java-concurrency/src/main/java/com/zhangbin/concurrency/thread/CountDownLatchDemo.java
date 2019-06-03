package com.zhangbin.concurrency.thread;

import java.util.concurrent.CountDownLatch;

/**
 * @author zhangbin
 * @Type CountDownLatchDemo
 * @Desc
 * @date 2019-05-22
 * @Version V1.0
 */
public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(4);

        Worker worker1 = new Worker(1000, countDownLatch, "worker-1");
        Worker worker2 = new Worker(2000, countDownLatch, "worker-2");
        Worker worker3 = new Worker(1000, countDownLatch, "worker-3");
        Worker worker4 = new Worker(4000, countDownLatch, "worker-4");

        worker1.start();
        worker2.start();
        worker3.start();
        worker4.start();

        countDownLatch.await();

        System.out.println(Thread.currentThread().getName() + " has finished!");

    }


    static class Worker extends Thread {

        private int delay;

        private CountDownLatch latch;

        public Worker(int delay, CountDownLatch latch, String name) {
            super(name);
            this.delay = delay;
            this.latch = latch;
        }

        @Override
        public void run() {

            try {
                System.out.println(Thread.currentThread().getName() + " has started" );
                Thread.sleep(delay);
                latch.countDown();
                System.out.println(Thread.currentThread().getName() + " has finished" );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }
}
