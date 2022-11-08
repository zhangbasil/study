package com.study.sftp;

import com.study.sftp.core.SftpTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:bin.zhang@itiaoling.com">zhangbin</a>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(value = {"classpath:application.properties"}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SftpTest {

    private final ThreadPoolExecutor executor = new ThreadPoolExecutor(30, 30, 60000, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(1000));


    @Autowired
    private SftpTemplate sftpTemplate;


    @Test
    public void execute() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 200; i++) {
            int taskIdx = i;
            executor.execute(() -> {

//                sftpTemplate.download("/static/demo/445k.pdf", "/Users/admin/Downloads/demo/" + taskIdx + ".pdf");

//                sftpTemplate.cd("/static/demo/");


                byte[] download = sftpTemplate.download("/static/demo/445k.pdf");


                System.out.println("线程：" + Thread.currentThread().getName() + " 执行 任务 :" + taskIdx);
            });
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        System.out.println(" ====> 花费时间：" + (System.currentTimeMillis() - start) / 1000 + " 秒");
    }

    @Test
    public void downloadOneTest() {
        byte[] download = sftpTemplate.download("/static/demo/445k.pdf");
        System.out.println("download = " + download);
    }


}
