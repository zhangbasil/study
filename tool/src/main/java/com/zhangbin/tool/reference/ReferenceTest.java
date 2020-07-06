package com.zhangbin.tool.reference;

import java.lang.ref.WeakReference;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 * <p>
 * java 四种引用
 *
 * 强引用：new关键字 不会被回收
 * 软引用：当内存不足时，会被回收。适合做缓存
 * 弱引用：只要进行垃圾回收的时候，就会被回收。ThreadLocal
 * 虚引用：管理堆外内存,当堆内引用直接内存被回收时，会在队列中添加一个事件，通知清除堆外内存
 *
 */
public class ReferenceTest {

    public static void main(String[] args) {

        WeakReference<Object> wr = new WeakReference<>(new Object());

        System.out.println("wr.get() = " + wr.get());


        System.gc();

        System.out.println("wr.get() = " + wr.get());


    }
}
