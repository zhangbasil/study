package com.zhangbin.tool.thread.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock 可重入锁
 *
 *  加锁 --> 通过Unsafe cas
 *
 *  如何做到可重入：通过state类似一个计数器 加一次锁值加一 解锁就减一
 *
 *
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
public class ReentrantLockTest {

    public static void main(String[] args) {

        reentrant();

    }


    public static void reentrant() {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        try {
            //doSomething
            lock.lock();
            try {
                System.out.println("doSomething");
                //doSomething
            } finally {
                lock.unlock();
            }

        } finally {
            lock.unlock();
        }
    }

}
