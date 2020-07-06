package com.zhangbin.tool.thread;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
public class Worker implements Runnable {
    @Override
    public void run() {
        System.out.println(" run ");
        runWorker(this);
    }

    private void runWorker(Worker worker) {
        System.out.println(" runWorker ");
        worker.run();
    }
}
