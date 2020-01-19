package com.zhangbin.threadlocal.adapter;

import com.zhangbin.threadlocal.model.ReqContext;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhangbin
 * @Type ThreadLocalHandlerInterceptorAdapter
 * @Desc
 * @date 2019-03-01
 * @Version V1.0
 */
@Component
public class ThreadLocalHandlerInterceptorAdapter extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ReqContext reqContext = ReqContext.get();
        String val = request.getParameter("val");
        reqContext.setVal(val);



//        Map<String, HttpServletRequest> requestMap = new HashMap<>();
//
//        reqContext.setContainer(requestMap);

        return super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {

        ReqContext.clean();
    }
}
