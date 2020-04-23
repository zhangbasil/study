package com.zhangbin.tool.array;

import java.util.Arrays;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
public class ArrayUtils {

    public static void main(String[] args) {

        int[] a = {1, 2, 3,};
        int[] b = {7, 9};

        int[] merge = merge(a, b);
        System.out.println("merge = " + Arrays.toString(merge));
    }


    private static int[] merge(int[] a, int[] b) {
        int[] newArr = Arrays.copyOf(a, a.length + b.length);
        System.arraycopy(b, 0, newArr, a.length, b.length);
        return newArr;
    }

}
