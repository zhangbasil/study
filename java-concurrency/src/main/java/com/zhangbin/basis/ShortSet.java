package com.zhangbin.basis;

import java.io.File;
import java.util.*;

/**
 * @author zhangbin
 * @Type ShortSet
 * @Desc
 * @date 2019-06-01
 * @Version V1.0
 */
public class ShortSet {


    private String abc;
    private static final String str = "sd";
    public ShortSet() {

    }

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

        int a = 1, b, c;


        ShortSet shortSet = new ShortSet();


        String abc = shortSet.abc;

    }



    public static int getNum(int n) {



        int x = 0;

        do {

            n = n / 10;
            System.out.println("n = " + n);

            x++;

        } while (n != 0);

        return x;
    }


    static void max() {

        int x = 2, y = 9, z = 1;
        if (x >= y)
            if (x>=z)
                System.out.println("x = " + x);
            else
                System.out.println("z = " + z);
        else
            if (y>=z)
                System.out.println("y = " + y);
            else
                System.out.println("z = " + z);
    }
}
