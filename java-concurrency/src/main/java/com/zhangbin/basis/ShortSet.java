package com.zhangbin.basis;

import java.util.HashSet;
import java.util.Set;

/**
 * @author zhangbin
 * @Type ShortSet
 * @Desc
 * @date 2019-06-01
 * @Version V1.0
 */
public class ShortSet {

    /***
     *
     *
     * (a) 1
     * (b) 100
     * (c) Throws exception
     * (d) None of the above
     *
     * @param args
     *
     *
     * The set contains Short values, but we’re removing Integer values
     *
     *
     */

    public static void main(String[] args) {
        Set<Short> s = new HashSet<>();
        for (short i = 0; i < 100; i++) {
            s.add(i);
            s.remove(i -1); // // int-valued expression
//            s.remove((short) (i - 1)); //Fixed it

        }
        System.out.println(s.size());
    }
}
