package com.zhangbin.concurrency.lock;

import java.util.Scanner;

/**
 * @author zhangbin
 * @Type VolatileQuestion
 * @Desc
 * @date 2019-05-18
 * @Version V1.0
 */
public class VolatileQuestion {

    private static boolean running = true;


    public static void main(String[] args) {

        Processor proc1 = new Processor();
        proc1.start();

        Processor proc2 = new Processor();
        proc2.start();

        Processor proc3 = new Processor();
        proc3.start();

        System.out.println(" Press return to stop ... ");

        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        proc1.shutdown();

    }


    static class Processor extends Thread {



        @Override
        public void run() {

            while (running) {
                System.out.println(Thread.currentThread().getName() + " Hello ====> ");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void shutdown() {
            running = false;
        }
    }

}

