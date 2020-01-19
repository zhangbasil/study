package com.zhangbin.threadlocal.service;

import org.springframework.scheduling.annotation.Async;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
public interface ThreadLocalService {

    @Async
    void hello();

}
