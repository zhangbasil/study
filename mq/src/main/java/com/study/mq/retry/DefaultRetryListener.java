package com.study.mq.retry;

import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;

import java.util.Objects;

/**
 * @author <a href="mailto:bin.zhang@itiaoling.com">zhangbin</a>
 */
public class DefaultRetryListener implements RetryListener {

    @Override
    public <T, E extends Throwable> boolean open(RetryContext context, RetryCallback<T, E> callback) {
        return true;
    }

    @Override
    public <T, E extends Throwable> void close(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
        int retryCount = context.getRetryCount();
        if (Objects.nonNull(throwable)) {
            System.out.println("重试第 " + retryCount + " 次还是失败！需要持久化。" + throwable.getMessage());
        }
    }

    @Override
    public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {

    }
}
