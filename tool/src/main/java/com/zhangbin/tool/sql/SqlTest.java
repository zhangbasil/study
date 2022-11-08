package com.zhangbin.tool.sql;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
public class SqlTest {

    public static void main(String[] args) {


        String sqls = "深圳正诚比亚迪新能源运营车专修店\n" +
                "佛山安运比亚迪专修店\n" +
                "广州市番禺新造停车场\n" +
                "花屿路库房\n" +
                "杭州利骏4S店\n" +
                "杭州亚迪4S店\n" +
                "宁波迪之杰4S店\n" +
                "宁波慧迪4S店\n" +
                "温州正迪4s店\n" +
                "义乌金星村停车场\n" +
                "嘉兴卓景汽车销售服务有限公司\n" +
                "南京朗迪/南京盛世新景4S店\n" +
                "湖南大汉无忧智慧科技有限公司\n" +
                "无锡市金凤汽车销售服务有限公司\n" +
                "青岛瑞迪汽车销售有限公司\n" +
                "南通沿江路停车场\n" +
                "湖南明洋新能源旗舰店\n" +
                "湖南海晟通比亚迪4S店\n" +
                "重庆绿能潜行比亚迪营运车专修店\n" +
                "重庆尚盈海冠比亚迪4S店\n" +
                "武汉中顺比亚迪专修店";


        String[] arr = sqls.split("\n");
        for (int i = 0; i < arr.length; i++) {
            String name = arr[i];

            String sql = "INSERT INTO `sys_dict_code`(`dict_code`, `dict_desc`, `dict_orderby`, `parent_code`, `display_desc`, `display_detail`,\n" +
                    "                            `extend`, `remark`, `create_by`, `status`, `gmt_create`, `gmt_modify`)\n" +
                    "VALUES ('2210" + (i + 1) + "', '" + name + "', " + (i + 1) + ", '221', '" + name + "', '" + name + "', '', '', 'sys', 1, now(), now());";


            System.out.println(sql);
        }

    }


}
