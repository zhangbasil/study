package com.zhangbin.threadlocal.model;

import java.util.Map;

/**
 * @author zhangbin
 * @Type ReqContext
 * @Desc
 * @date 2019-03-01
 * @Version V1.0
 */
public class ReqContext {


    private static final ThreadLocal<ReqContext> LOCAL = ThreadLocal.withInitial(ReqContext::new);


    private String val;

    private Map<?, ?> container;


    public static ReqContext get() {
        return LOCAL.get();
    }

    public static void clean() {
        LOCAL.remove();
    }


    public Map<?, ?> getContainer() {
        return container;
    }

    public void setContainer(Map<?, ?> container) {
        this.container = container;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}
