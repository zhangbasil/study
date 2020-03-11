package com.zhangbin.tool.lombok;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
public class LombokTest {


    public static void main(String[] args) {
        User user1 = new User();
        user1.setName("张三");

        User user2 = new User();
        user2.setName("李四");
        user2.setOther(user1);

        user1.setOther(user2);

        System.out.println("user1 = " + user1.toString());
    }

    @Data
    @EqualsAndHashCode(exclude = {"other"})
    @ToString(exclude = {"other"})
    @FieldDefaults(level = AccessLevel.PRIVATE)
    static class User {
        String name;
        User other;
    }

}
