package com.zhangbin.threadlocal;

import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhangbin
 * @Type Test
 * @Desc
 * @date 2019-03-03
 * @Version V1.0
 */
public class Test {

    // 线程数
    private static final int THREAD_COUNT = 200;

    // 线程池
    private static final ExecutorService pool = Executors.newFixedThreadPool(THREAD_COUNT);

    private static final CountDownLatch downLatch = new CountDownLatch(THREAD_COUNT);



    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < THREAD_COUNT; i++) {

            pool.execute(()->{

                try {
                    // 等待全部线程到达
                    downLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                doWork();


            });

            downLatch.countDown();
        }

        pool.shutdown();

        while (true) {
            if (pool.isTerminated()) {
                System.out.println(" =线程全部执行完成= ");
                break;
            }
            Thread.sleep(200);
        }



    }

    private static void doWork() {
        sendReq();
    }


    private static void sendReq() {
        String url = "http://localhost:8080/index?val=" + Thread.currentThread().getId();
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(url, String.class);

    }

}
