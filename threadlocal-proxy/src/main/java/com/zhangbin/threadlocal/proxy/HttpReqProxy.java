package com.zhangbin.threadlocal.proxy;

import com.zhangbin.threadlocal.annotation.RequestLine;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author zhangbin
 * @Type HttpReqProxy
 * @Desc
 * @date 2019-03-04
 * @Version V1.0
 */
public class HttpReqProxy implements InvocationHandler {

    private final RestTemplate restTemplate = new RestTemplate();

    private final String url;

    public HttpReqProxy(String url) {
        this.url = url;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        Class<?> returnType = method.getReturnType();

        RequestLine requestLine = AnnotationUtils.getAnnotation(method, RequestLine.class);
        if (requestLine == null) {
            return proxy;
        }
        String path = requestLine.path();
        RequestLine.RequestMethod requestMethod = requestLine.method();
        return execute(path, requestMethod, args, returnType);
    }



    private Object execute(String path, RequestLine.RequestMethod requestMethod, Object[] args, Class<?> returnType) {
        String reqPath = this.url + "/" + path;
        if (RequestLine.RequestMethod.GET.equals(requestMethod)) {
            return restTemplate.getForObject(reqPath, returnType, args);
        }
        return restTemplate.postForObject(reqPath, returnType, null);

    }

}
