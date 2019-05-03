package com.zhangbin.concurrency.sync;

/**
 * @author zhangbin
 * @Type SynchronizedQuestion
 * @Desc
 * @date 2019-05-01
 * @Version V1.0
 */
public class SynchronizedQuestion {

    synchronized void sync() {
        doSomething();
    }


    void onlyMe(Foo foo) {
        synchronized (foo) {
            doSomething();
        }
    }

    private void doSomething() {

    }

    private class Foo {
    }
}
