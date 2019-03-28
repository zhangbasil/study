package com.zhangbin.threadlocal.core;

import com.zhangbin.threadlocal.proxy.HttpReqProxy;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;

/**
 * @author zhangbin
 * @Type RequestServerFactoryBean
 * @Desc
 * @date 2019-03-24
 * @Version V1.0
 */
public class RequestServerFactoryBean<T> implements FactoryBean<T> {


    private Class<T> requestInterface;

    private String requestUrl;


    public RequestServerFactoryBean(Class<T> requestInterface, String requestUrl) {
        this.requestInterface = requestInterface;
        this.requestUrl = requestUrl;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T getObject() {
        return (T) Proxy.newProxyInstance(requestInterface.getClassLoader(), new Class[]{requestInterface},
                new HttpReqProxy(this.requestUrl));
    }

    @Override
    public Class<?> getObjectType() {
        return this.requestInterface;
    }

}
