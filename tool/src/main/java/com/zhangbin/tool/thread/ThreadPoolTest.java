package com.zhangbin.tool.thread;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
public class ThreadPoolTest {

    public static void main(String[] args) throws IOException, InterruptedException {

        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 5, 60000, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(20));

        executor.execute(() -> {

        });


    }

    public static void inner() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();

        lock.lock();
        try {
            //doSomething
            Thread.sleep(3000);
        } finally {
            lock.unlock();
        }

    }
}
