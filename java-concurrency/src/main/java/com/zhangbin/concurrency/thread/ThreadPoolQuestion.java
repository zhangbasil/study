package com.zhangbin.concurrency.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangbin
 * @Type ThreadPoolQuestion
 * @Desc
 * @date 2019-04-17
 * @Version V1.0
 */
public class ThreadPoolQuestion {

    public static void main(String[] args) {

//        threadPoolExecutor();
//        forkJoinPool();
        parallelStream();





    }



    private static void parallelStream() {
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            strings.add("task" + i);
        }

        strings.parallelStream().forEach(s -> action());


    }


    private static void threadPoolExecutor() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(20, 20, 0, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());
        for (int i = 0; i < 100; i++) {
            executor.submit(ThreadPoolQuestion::action);
        }

//        try {
//            executor.awaitTermination(2000, TimeUnit.MILLISECONDS);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        // 关闭线程池
        executor.shutdown();
    }

    private static void keepAliveTime() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 5, 60000, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(5));

        for (int i = 0; i < 10; i++) {
            if (i > 7) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            executor.submit(ThreadPoolQuestion::action);
        }
        executor.shutdown();
    }


    private static void forkJoinPool() {

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        for (int i = 0; i < 100; i++) {
            forkJoinPool.submit(ThreadPoolQuestion::action);
        }



        try {
            Thread.sleep(200000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        forkJoinPool.shutdown();
    }

    private static void action() {
        System.out.println("线程 [" + Thread.currentThread().getName() + "] 执行了这个任务.... ");
    }

}
