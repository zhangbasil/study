package com.zhangbin.tool.array;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.List;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
public class ArrayUtils {

    public static void main(String[] args) {

        int[] a = {1, 2, 3,};
        int[] b = {7, 9};

        int[] merge = merge(a, b);
        System.out.println("merge = " + Arrays.toString(merge));

        List<GarbageCollectorMXBean> beans = ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean bean : beans) {
            System.out.println(bean.getName());
        }

    }


    private static int[] merge(int[] a, int[] b) {
        int[] newArr = Arrays.copyOf(a, a.length + b.length);
        System.arraycopy(b, 0, newArr, a.length, b.length);
        return newArr;
    }

}
