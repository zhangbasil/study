package com.zhangbin.tool.lombok;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
public class LombokTest {


    public static void main(String[] args) throws ParseException {


        Date effectiveEndTime = new Date();
        Date date1 = DateUtils.setSeconds(DateUtils.setMinutes(DateUtils.setHours(effectiveEndTime, 23), 59), 59);


        System.out.println("date1 = " + date1);
        System.out.println("effectiveEndTime = " + effectiveEndTime);


        LocalDate now = LocalDate.now();

        Date date = DateUtils.parseDate(String.valueOf(now), "yyyy-MM-dd");

        System.out.println("date = " + date);


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
