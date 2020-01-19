package com.zhangbin.concurrency.thread.threadlocal;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.threadpool.TtlExecutors;

import java.util.concurrent.*;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
public class ThreadLocalDemo {

    private static final ThreadLocal<Integer> LOCAL = new ThreadLocal<>();

    private static final InheritableThreadLocal<Integer> INHERITABLE_THREAD_LOCAL = new InheritableThreadLocal<>();

    private static final TransmittableThreadLocal<Integer> TRANSMITTABLE_THREAD_LOCAL = new TransmittableThreadLocal<>();

    private static ExecutorService executorService = Executors.newFixedThreadPool(5);

    private static ExecutorService forkJoinPool = new ForkJoinPool();



    public static void main(String[] args) throws InterruptedException {


        threadLocalCase();

        Thread.sleep(3000L);
    }


    private static void threadLocalCase() {

//        LOCAL.set(100);

//        INHERITABLE_THREAD_LOCAL.set(50000);


//        new Thread(() -> {
//            System.out.println("threadLocal = " + LOCAL.get());
//            System.out.println("inheritable_thread_local = " + INHERITABLE_THREAD_LOCAL.get());
//        }, "inheritable_thread_local").start();
//
//
//        CompletableFuture.runAsync(() -> {
//            System.out.println("CompletableFuture->threadLocal = " + LOCAL.get());
//            System.out.println("CompletableFuture->inheritable_thread_local = " + INHERITABLE_THREAD_LOCAL.get());
//        });




//        executorService = TtlExecutors.getTtlExecutorService(executorService);
        ExecutorService executorService = TtlExecutors.getTtlExecutorService(forkJoinPool);


        for (int i = 0; i < 100; i++) {
//            INHERITABLE_THREAD_LOCAL.set(5000 + (i + 1));
            if (i > 90) {
                System.out.println();
            }
            TRANSMITTABLE_THREAD_LOCAL.set(5000 + (i + 1));
            executorService.submit(() -> {
//                System.out.println("inheritable_thread_local " + Thread.currentThread().getName() + "   ，value ：" + INHERITABLE_THREAD_LOCAL.get());
                System.out.println("TRANSMITTABLE_THREAD_LOCAL " + Thread.currentThread().getName() + "   ，value ：" + TRANSMITTABLE_THREAD_LOCAL.get());
            });
        }


    }


}
