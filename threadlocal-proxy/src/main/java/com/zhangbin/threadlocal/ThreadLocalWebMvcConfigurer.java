package com.zhangbin.threadlocal;

import com.zhangbin.threadlocal.adapter.ThreadLocalHandlerInterceptorAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author zhangbin
 * @Type ThreadLocalWebMvcConfigurer
 * @Desc
 * @date 2019-03-01
 * @Version V1.0
 */
@Configuration
public class ThreadLocalWebMvcConfigurer implements WebMvcConfigurer {

    @Resource
    private ThreadLocalHandlerInterceptorAdapter threadLocalHandlerInterceptorAdapter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(threadLocalHandlerInterceptorAdapter);
    }

}
