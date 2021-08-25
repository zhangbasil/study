package com.zhangbin.tool.hash;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
public class ConsistentHash {


    public static void main(String[] args) {


        TreeMap<Integer, String> data = new TreeMap<>();

        data.put(1, "111111");
        data.put(3, "33333");
        data.put(5, "55555");
        data.put(7, "77777");
        data.put(9, "3333");
        data.put(13, "55555");


        Map.Entry<Integer, String> entry = data.ceilingEntry(14);

        System.out.println("=======> " + entry);

    }


}
