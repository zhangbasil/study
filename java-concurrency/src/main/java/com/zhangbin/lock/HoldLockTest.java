package com.zhangbin.lock;

/**
 * 检查线程是否拥有锁
 *
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
public class HoldLockTest {

    private static final Object object = new Object();

    public static void main(String[] args) {




    }


    private static void holdLock() {
        new Thread(() -> {
            synchronized (object) {
                System.out.println(Thread.currentThread().getName() + " 拥有锁：" + Thread.holdsLock(object));
            }
        }).start();

        System.out.println(Thread.currentThread().getName() + " 拥有锁：" + Thread.holdsLock(object));
    }

}
