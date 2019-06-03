package com.zhangbin.spi;

/**
 * @author zhangbin
 * @Type Bumblebee
 * @Desc
 * @date 2019-05-04
 * @Version V1.0
 */
public class Bumblebee implements Robot {
    @Override
    public void sayHello() {
        System.out.println("Hello, I am Bumblebee.");
    }
}
