package com.zhangbin.threadlocal;

import com.zhangbin.threadlocal.adapter.ThreadLocalHandlerInterceptorAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.concurrent.Executor;

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


    @Bean
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setTaskDecorator(new ContextCopyingDecorator());
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(5);
        executor.setQueueCapacity(100);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setThreadNamePrefix("zhangbin-");
        executor.initialize();
        return executor;
    }


}
