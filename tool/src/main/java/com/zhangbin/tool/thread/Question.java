package com.zhangbin.tool.thread;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
public class Question {


    static final Object o1 = new Object();
    static final Object o2 = new Object();


    public static void main(String[] args) throws Exception {
//        orderExec();
//        Thread.sleep(5_000);

//        deadLock();

        new LoopThread("t1").start();
        new LoopThread("t2").start();


    }


    /**
     * 2个线程交叉依次打印1...10
     */
    static class LoopThread extends Thread {

        volatile int state = 0;// 0打印偶数 1打印奇数


        public LoopThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                if (state == 0) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    state = 1;
                    notify();
                }
                System.out.println(Thread.currentThread().getName() + "  " + i);
            }
        }
    }

    /**
     * jps
     * <p>
     * jstack
     * <p>
     * jconsole
     * <p>
     * jvisualvm
     */
    public static void deadLock() {
        DeadLock deadLock1 = new DeadLock(0);
        DeadLock deadLock2 = new DeadLock(1);
        Thread thread1 = new Thread(deadLock1);
        Thread thread2 = new Thread(deadLock2);
        thread1.start();
        thread2.start();
    }


    /**
     * T1 t2 t3 顺序执行
     *
     * @throws InterruptedException
     */
    public static void orderExec() throws InterruptedException {
        T1 t1 = new T1("T1");
        T2 t2 = new T2("T2");
        T3 t3 = new T3("T3");
        t1.start();
        t1.join();
        System.out.println(t1.isAlive());
        System.out.println("t1.isInterrupted() = " + t1.isInterrupted());
        t2.start();
        t2.join();
        t3.start();
    }


    static class DeadLock implements Runnable {
        int flag;

        public DeadLock(int flag) {
            this.flag = flag;
        }

        @Override
        public void run() {
            System.out.println("flag: " + flag);

            // deadLock2占用资源o1，准备获取资源o2
            if (flag == 1) {
                synchronized (o1) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (o2) {
                        System.out.println("1");
                    }
                }
            }

            // deadLock1占用资源o2，准备获取资源o1
            else if (flag == 0) {
                synchronized (o2) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (o1) {
                        System.out.println("0");
                    }
                }
            }
        }

    }

    static class T1 extends Thread {

        public T1(String name) {
            super(name);
        }

        @Override
        public void run() {
            System.out.println(" 线程T1 开始执行" + Thread.currentThread().getName());
        }
    }

    static class T2 extends Thread {
        public T2(String name) {
            super(name);
        }

        @Override
        public void run() {
            System.out.println(" 线程T2 开始执行" + Thread.currentThread().getName());
        }
    }

    static class T3 extends Thread {

        public T3(String name) {
            super(name);
        }

        @Override
        public void run() {
            System.out.println(" 线程T3 开始执行" + Thread.currentThread().getName());
        }
    }

}
