package com.zhangbin.concurrency.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangbin
 * @Type CountDownLatchQuestion
 * @Desc
 *
 *
 *
 * @date 2019-04-16
 * @Version V1.0
 */
public class CountDownLatchQuestion {



    public static void main(String[] args) throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(10);

//        ExecutorService executorService = Executors.newFixedThreadPool(10);


        /*
         *
         *  corePoolSize 核心线程数N，先创建N个核心线程
         *
         *
         * BlockingQueue 阻塞队列，
         *
         * maximumPoolSize 最大线程数M
         *
         *
         *
         * 先创建核心N个核心线程数，当达到核心线程数后，会进入阻塞队列中，当达到阻塞队列的最大个数后，再创建线程达到最大线程数
         *
         *
         *
         */

//        ForkJoinPool executorService = new ForkJoinPool();

        ExecutorService executorService = new ThreadPoolExecutor(
                2, 5, 2, TimeUnit.SECONDS, new ArrayBlockingQueue<>(5));


//        ThreadPoolExecutor executorService = new ThreadPoolExecutor(2, 4, 0, TimeUnit.MILLISECONDS,
////                new LinkedBlockingDeque<>());
//                new ArrayBlockingQueue<>(40));

        for (int i = 0; i < 10; i++) {
            if (i > 2) {
                Thread.sleep(500);
            }
            executorService.submit(() -> {
                action();
                countDownLatch.countDown();
            });
        }

        countDownLatch.await();
        System.out.println(" Done ");

        // 关闭线程
        executorService.shutdown();


    }


    private static void action() {
        System.out.println(" 线程 [" + Thread.currentThread().getName() + "] 执行 了这个任务.... ");
    }



}
