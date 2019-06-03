package com.zhangbin.concurrency.thread.communication;

import java.util.Queue;

/**
 * @author zhangbin
 * @Type Consumer
 * @Desc
 * @date 2019-05-18
 * @Version V1.0
 */
public class Consumer extends Thread {

    private final Queue<Integer> shareQ;

    public Consumer(Queue<Integer> shareQ) {
        this.shareQ = shareQ;
    }

    @Override
    public void run() {

        while (true) {
            synchronized (shareQ) {
                while (shareQ.size() == 0) {
                    try {
                        System.out.println(" Consumer Queue is empty , waiting.... " );
                        shareQ.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                Integer number = shareQ.poll();

                System.out.println("Consumers ===> " + number);
                shareQ.notify();

//                if (number == 3) {
//                    break;
//                }

            }
        }

    }
}
