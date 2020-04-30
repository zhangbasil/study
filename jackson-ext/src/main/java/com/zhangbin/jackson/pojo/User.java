package com.zhangbin.jackson.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.zhangbin.jackson.core.DefaultJacksonAnnotationIntrospector;
import com.zhangbin.jackson.core.filter.DefaultPropertyFilter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
@Getter
@Setter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@ApiModel("user")
public class User {

    @ApiModelProperty("用户Id")
    Long userId;
    @ApiModelProperty("用户名称")
    String userName;
    @JsonIgnore
    @ApiModelProperty("密码")
    String password;
    //    @Mask(left = 3, right = 4)
    @ApiModelProperty("手机号")
    String mobile;

    @ApiModelProperty("创建时间")
    Date createDate;

    @ApiModelProperty("更新时间")
    LocalDate updateDate;

    @ApiModelProperty("修改时间")
    LocalDateTime modifyTime;

    @ApiModelProperty("分享用户")
    User share;

    public static void main(String[] args) throws JsonProcessingException {
        User share = User.builder().userId(9999L).userName("分享人").password("1000000").mobile("188888776").build();
        User user = User.builder()
                .userId(10000L)
                .userName("张三丰")
                .password("666888")
                .mobile("17612346666")
                .share(share)
                .build();
        JsonMapper jsonMapper = JsonMapper.builder().build();
        jsonMapper.setAnnotationIntrospector(new DefaultJacksonAnnotationIntrospector());
        jsonMapper.addMixIn(Object.class, DefaultPropertyFilter.class);
        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
//        filterProvider.addFilter(IntegrationPropertyFilter.FILTER_ID, new IntegrationPropertyFilter(null));
        jsonMapper.setFilterProvider(filterProvider);
        String userStr = jsonMapper.writer().writeValueAsString(user);
        System.out.println("userStr = " + userStr);
    }

}
