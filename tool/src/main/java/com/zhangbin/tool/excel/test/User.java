package com.zhangbin.tool.excel.test;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zhangbin.tool.excel.annotatoin.ExcelField;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Objects;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class User {


    @ExcelField(headName = "用户ID")
    String customerId;


    @ExcelField(headName = "用户身份证号码")
    String idNumber;

    @ExcelField(headName = "用户姓名")
    String realName;

    @ExcelField(headName = "用户昵称")
    String nickName;

    @ExcelField(headName = "身份证正面照片")
    String idUpside;

    @ExcelField(headName = "身份证反面照片")
    String idInverse;

    @ExcelField(headName = "驾照等级")
    String driverLicenseLevel;

    @ExcelField(headName = "驾照正面照片")
    String driverUpside;

    @ExcelField(headName = "驾照反面照片")
    String driverInverse;

    @ExcelField(headName = "创建时间")
    String gmtCreate;

    @ExcelField(headName = "转介绍人")
    String introductionName;

    @ExcelField(headName = "转介绍电话")
    String introductionMobile;


    @ExcelField(headName = "用户手机号")
    String mobile;

    @ExcelField(headName = "用户图像")
    String headImg;

    @JsonIgnore
    @ExcelField(headName = "推荐人姓名")
    String shareName;

    @JsonIgnore
    @ExcelField(headName = "推荐人手机号")
    String shareMobile;

    @ExcelField(headName = "是否有合同")
    String hasContract;

    @ExcelField(headName = "用户推荐码")
    String shareCode;


    User sharer;


    public String getShareName() {
        return Objects.nonNull(sharer) ? sharer.getRealName() : "";
    }

    public String getShareMobile() {
        return Objects.nonNull(sharer) ? sharer.getMobile() : "";
    }
}
