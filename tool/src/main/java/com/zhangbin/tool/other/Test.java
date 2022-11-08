package com.zhangbin.tool.other;

import java.util.Arrays;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
public class Test {

    public static void main(String[] args) {
        int[] arr = {1, 3, 4, 6, 8, 9, 10, 23, 44, 55, 66};
        search(arr, 1000);
    }
    private static void search(int[] arr, int number) {
        System.out.println("arr = " + Arrays.toString(arr));
        int len = arr.length / 2;
        int item = arr[len];
        if (item == number) {
            System.out.println(" ok 存在" + number);
            return;
        }
        if (arr.length == 1) {
            System.out.println("不存在" + number);
            return;
        }
        int[] newArr;
        if (item > number) {
            newArr = Arrays.copyOf(arr, len);
        } else {
            newArr = Arrays.copyOfRange(arr, len, arr.length);
        }
        search(newArr, number);
    }


}
