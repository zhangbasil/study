package com.zhangbin.concurrency.lombok;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
public class Test {

    public static void main(String[] args) {

        Student.builder().course("")
                .id(1L)
                .score("").build();

        Student student = new Student();

        Teacher build = Teacher.builder().isMaster(true).build();

        System.out.println("student = " + student);

    }
}
