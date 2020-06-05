package com.zhangbin.es.pojo;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.elasticsearch.common.Nullable;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.Instant;
import java.time.LocalDate;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Document(indexName = "person")
public class Person {

    @Nullable
    @Id
    private Long id;

    @Nullable
    @Field(value = "last-name", type = FieldType.Text, fielddata = true)
    private String lastName;

    @Field(type = FieldType.Keyword)
    private String mobile;

    @Field(type = FieldType.Keyword)
    private String address;

    @Nullable
    @Field(name = "birth-date", type = FieldType.Date, format = DateFormat.basic_date)
    private LocalDate birthDate;

    @CreatedDate
    @Nullable
    @Field(type = FieldType.Date, format = DateFormat.basic_date_time)
    private Instant created;

}
