package com.zhangbin.concurrency.thread.communication;

import java.util.Queue;

/**
 * @author zhangbin
 * @Type Producer
 * @Desc
 * @date 2019-05-18
 * @Version V1.0
 */
public class Producer extends Thread {

    private final Queue<Integer> shareQ;


    public Producer(Queue<Integer> shareQ) {
        this.shareQ = shareQ;
    }

    @Override
    public void run() {
        for (int i = 0; i < 4; i++) {
            synchronized (shareQ) {
                //waiting condition - wait until Queue is not empty
                while (shareQ.size() > 0) {
                    try {
                        shareQ.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(" Produce ===> " + i );
                shareQ.add(i);
                shareQ.notify();
            }
        }
    }
}
