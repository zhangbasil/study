package com.zhangbin.tool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
@SpringBootApplication(scanBasePackages = {"com.zhangbin.tool"})
public class ToolApplication {

    @Bean
    @ConditionalOnMissingBean(name = "objectRedisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    public static void main(String[] args) {
        SpringApplication.run(ToolApplication.class, args);
    }
}
