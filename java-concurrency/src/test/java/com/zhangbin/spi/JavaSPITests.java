package com.zhangbin.spi;

import org.junit.Test;

import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author zhangbin
 * @Type JavaSPITests
 * @Desc
 * @date 2019-05-04
 * @Version V1.0
 */
public class JavaSPITests {

    @Test
    public void sayHello() throws Exception {
//        ServiceLoader<Robot> serviceLoader = ServiceLoader.load(Robot.class);
//        System.out.println("Java SPI");
//        serviceLoader.forEach(Robot::sayHello);


        Class<?> robot = ClassLoader.getSystemClassLoader().loadClass("com.zhangbin.spi.Robot");


        Object o1 = robot.getDeclaredConstructor().newInstance();

        System.out.println("o1 = " + o1);


        Class<?> clazz = ClassLoader.getSystemClassLoader().loadClass("com.zhangbin.spi.OptimusPrime");


        OptimusPrime o = (OptimusPrime) clazz.getDeclaredConstructor().newInstance();
        o.sayHello();


        ServiceLoader<OptimusPrime> load = ServiceLoader.load(OptimusPrime.class);

        load.iterator().next().sayHello();
    }

    /**
     * 冒泡排序
     *
     * 原理：相邻的两个元素进行比较，按照从小到大或者从大到小的顺序进行交换（数组下标 1 2 -> 2 3 -> 3 4 -> 4 5 ... n-1 n 比较）
     */
    @Test
    public void bubbleSort() {
        int[] arr = {6, 4, 3, 8, 0, 7};
        System.out.println("原生数组：" + Arrays.toString(arr));
        for (int j = 0; j < arr.length; j++) {
            // 是否提交结束排序
            boolean isOver = true;
//            for (int i = 0; i < arr.length - 1; i++) {

            // 每趟循环结束就可以确定一个元素的位置，-j 提高效率
            for (int i = 0; i < arr.length - 1 - j; i++) {
                // 需要交换
                if (arr[i] > arr[i + 1]) {
                    isOver = false;
                    int temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                }
                System.out.println("第 " + (i + 1) + " 比较的结果 " + Arrays.toString(arr));
            }

            System.out.println(" ================ " + (j + 1));
            if (isOver) {
                break;
            }
        }
        System.out.println("排序后数组：" + Arrays.toString(arr));
    }
}
