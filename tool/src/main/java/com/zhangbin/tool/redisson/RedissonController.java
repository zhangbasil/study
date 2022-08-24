package com.zhangbin.tool.redisson;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 *
 *
 */
@RestController
@RequestMapping("/redisson")
public class RedissonController {
    @Autowired
    private RedissonClient redisson;

    @GetMapping("/")
    public String demo() {
        RLock lock = redisson.getLock("myLock");
        if (lock.tryLock()) {
            System.out.println("获得锁成功！");
            try {
                Thread.sleep(100_1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return "ok";
    }

}
