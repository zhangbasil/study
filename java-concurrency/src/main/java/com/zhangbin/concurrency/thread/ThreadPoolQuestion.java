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

    private static final int COUNT_BITS = Integer.SIZE - 3;
    private static final int CAPACITY   = (1 << COUNT_BITS) - 1;

    // runState is stored in the high-order bits
    private static final int RUNNING    = -1 << COUNT_BITS;

    public static void main(String[] args) {

//        threadPoolExecutor();
//        forkJoinPool();
//        parallelStream();


        String rocketmq_home = System.getenv("ROCKETMQ_HOME");
        System.out.println("env = " + rocketmq_home);


    }

    private static int ctlOf(int rs, int wc) { return rs | wc; }



    private static void parallelStream() {
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < 200000; i++) {
            strings.add("task" + i);
        }

        strings.parallelStream().forEach(s -> action());


    }


    private static void threadPoolExecutor() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 5, 0, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());
        for (int i = 0; i < 100; i++) {
            executor.submit(ThreadPoolQuestion::action);
        }

        executor.execute(ThreadPoolQuestion::action);

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
                new LinkedBlockingQueue<>(20));

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
        for (int i = 0; i < 100000; i++) {
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
        String name = Thread.currentThread().getName();
        if (name.contains("-3")) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("线程 [" + name + "] 执行了这个任务.... ");
    }

}
