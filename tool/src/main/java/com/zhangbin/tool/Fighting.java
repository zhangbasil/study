package com.zhangbin.tool;

import com.zhangbin.tool.excel.ExcelReader;
import lombok.Getter;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
public class Fighting {

    /*
     * 进入会议室坐下后
     *
     * 谢经理你好！请问有什么事情吗？
     *
     * HR：你违反了。。。。。公司把你开除？
     *
     * 你这边是代表公司还是个人？
     *
     * HR：公司
     *
     * 你现在在公司什么职位，可以代表公司做这个决定？
     *
     * HR：人事主管
     *
     * 没有看手机 没有炒股
     *
     * HR：啪 。。这是证据
     *
     * 看手机是因为很多和第三方对接都是用的微信，不是用的钉钉沟通，App接口开发完了也需要真机自测的
     * 炒股是没有的最多只是在工作完成情况下偶尔看一下，再说你这个规章制度也没见过，也没有提醒过。
     * 再说这也没有对公司造成经济损失
     * 如果因为这个小问题是我的错，我接受批评。
     *
     *
     * 如果公司以此为理由解除劳动合同，是违法的。根据劳动法公司应该支付我经济赔偿
     * 这就是变相违法裁员，那我这边只能去劳动局仲裁了，来维护我自己的权益了。
     *
     *
     * HR：给你一个月找工作
     * 我没有换工作的想法，公司是准备单方面和我解除合同吗？
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     */


    public static void main(String[] args) {
        File[] files = new File("/Users/admin/Documents/work/考勤").listFiles();
        List<Attendance> attendances = new ArrayList<>();
        for (File file : files) {
            String fileName = file.getName();
            List<String[]> data = ExcelReader.read(file.getPath(), 4);
            String date = fileName.split("_")[2].substring(0, 6);
            for (String[] strings : data) {
                String days = strings[5];
                String[] strs = strings[7].replace("分钟", "").split("小时");
                String hours = strs[0];
                String minutes = strs[1];

                int sumMinutes = 60 * Integer.parseInt(hours) + Integer.parseInt(minutes);

//                System.out.println("  " + date + "  出勤[" + days + "]天，工作[" + sumMinutes + "]分钟 " + " " +
//                        "加班[" + (sumMinutes - Integer.parseInt(days) * 9 * 60) + "]分钟");

                attendances.add(new Attendance(Integer.parseInt(date), Integer.parseInt(days), sumMinutes));

            }


        }


        int sum = attendances.stream().sorted(Comparator.comparingInt(a -> a.data))
                .collect(Collectors.toList()).stream().peek(attendance -> {
                    System.out.println(attendance.getData() + "出勤了【" + attendance.getDays() + "】天，" +
                            "工作【" + attendance.getMinutes() + "】分钟，加班【" + attendance.getOverTime() + "】分钟");
                }).mapToInt(Attendance::getOverTime).sum();

        float jiaban = sum / 60;

        System.out.println("总加班【" + sum + "】分钟 （" + (sum / 60) + "）小时");


        // 每小时32000/22/8
        float price = 32000 / (8 * 22);

        System.out.println("price = " + price);


        float jiabanfei = (float) (jiaban * price * 1.5);

        System.out.println("jiabanfei = " + jiabanfei);


    }


    @Getter
    static class Attendance {

        int data;

        int days;

        int minutes;

        int overTime;


        public Attendance(int data, int days, int minutes) {
            this.data = data;
            this.days = days;
            this.minutes = minutes;
            this.overTime = minutes - days * 9 * 60;
        }


    }





}
