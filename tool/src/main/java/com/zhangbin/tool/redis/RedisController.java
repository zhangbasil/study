package com.zhangbin.tool.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    RedisTemplate<String, Object> objectRedisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @GetMapping("/str")
    public String strSet() {
        stringRedisTemplate.opsForValue().set("name", "张斌");
        return "ok";
    }

    @GetMapping("/zset")
    public Boolean zset() {
        return objectRedisTemplate.opsForZSet().add("abc", "DDD", 100);
    }

}
