package com.zhangbin.threadlocal.service;

import com.zhangbin.threadlocal.model.ReqContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
@Slf4j
@Service
public class ThreadLocalServiceImpl implements ThreadLocalService {
    @Override
    public void hello() {
        String val = ReqContext.get().getVal();
    }
}
