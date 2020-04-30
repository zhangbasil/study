package com.zhangbin.tool.objectsize;

import java.lang.instrument.Instrumentation;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
public class SizeUtils {

    private static Instrumentation instrumentation;

    public static void premain(String args, Instrumentation inst) {
        instrumentation = inst;
    }

    public static long getObjectSize(Object o) {
        return instrumentation.getObjectSize(o);
    }

}
