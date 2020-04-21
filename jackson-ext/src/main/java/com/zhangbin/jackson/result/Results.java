package com.zhangbin.jackson.result;

import java.util.List;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
public final class Results {

    /**
     * 成功
     *
     * @return Result<Void>
     */
    public static Result<Void> success() {
        return new DefaultResult<Void>().setCode("0").setMessage("成功");
    }

    /**
     * 成功
     *
     * @param data 并设置data参数
     * @param <T>  data的泛型
     * @return Result<T>
     */
    public static <T> Result<T> success(T data) {
        return new DefaultResult<T>().setCode("0").setMessage("成功")
                .setData(data);
    }

    public static <T> Result<T> invalid() {
        return new DefaultResult<T>().setCode("C_1")
                .setMessage("参数错误");
    }

    public static <T> Result<T> invalid(String message) {
        return new DefaultResult<T>().setCode("C_1").setMessage(message);
    }

    public static <T> Result<T> invalid(String message, List<Result.ViolationItem> violationItems) {
        return new DefaultResult<T>().setCode("C_1").setMessage(message)
                .setViolationItems(violationItems);
    }

    public static <T> Result<T> invalid(List<Result.ViolationItem> violationItems) {
        return new DefaultResult<T>().setCode("C_1")
                .setMessage("参数错误").setViolationItems(violationItems);
    }


    /**
     * 返回带异常信息的响应结果，可以自己明确的系统错误
     *
     * @param code    错误编号
     * @param message 错误信息
     * @param <T>     对应data字段的数据类型
     * @return result 对象
     */
    public static <T> Result<T> error(String code, String message) {
        return new DefaultResult<T>().setCode(code).setMessage(message);
    }

    /**
     * 构建参数验证失败的项目
     *
     * @param fieldName 字段名称
     * @param message   信息
     * @return 参数验证失败的项目
     */
    public static Result.ViolationItem buildViolationItem(String fieldName, String message) {
        return new DefaultResult.DefaultViolationItem(fieldName, message);
    }

}
