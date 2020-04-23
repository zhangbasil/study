package com.zhangbin.jackson.result;


import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
public class DefaultResult<T> implements Result<T> {

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误消息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 参数校验错误信息
     */
    private List<ViolationItem> violationItems;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public T getData() {
        return data;
    }

    @Override
    public List<ViolationItem> getViolationItems() {
        return violationItems;
    }

    @Override
    public DefaultResult<T> setCode(String code) {
        this.code = code;
        return this;
    }

    @Override
    public DefaultResult<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public DefaultResult<T> setData(T data) {
        this.data = data;
        return this;
    }

    @Override
    public DefaultResult<T> setViolationItems(List<ViolationItem> violationItems) {
        this.violationItems = violationItems;
        return this;
    }

    @Override
    public DefaultResult<T> addViolationItem(String field, String message) {
        if (violationItems == null) {
            violationItems = new ArrayList<>();
        }
        violationItems.add(new DefaultViolationItem(field, message));
        return this;
    }

    @Override
    public boolean isSuccess() {
        return "0".equals(code);
    }


    public static class DefaultViolationItem implements ViolationItem {

        private String field;
        private String message;

        private transient Object invalidValue;

        public DefaultViolationItem(String field, String message) {
            this.field = field;
            this.message = message;
        }

        public DefaultViolationItem(String field, String message, Object invalidValue) {
            this.field = field;
            this.message = message;
            this.invalidValue = invalidValue;
        }


        @Override
        public String getField() {
            return field;
        }

        @Override
        public void setField(String field) {
            this.field = field;
        }

        @Override
        public String getMessage() {
            return message;
        }

        @Override
        public void setMessage(String message) {
            this.message = message;
        }

        @Override
        public String toString() {
            return "{"
                    + "field='" + field + '\''
                    + ", invalidValue='" + invalidValue + '\''
                    + ", message='" + message + '\'' + '}';
        }
    }

    @Override
    public String toString() {
        return "Result{"
                + "code='" + code + '\''
                + ", message='" + message + '\''
                + ", data=" + data
                + ", violationItems=" + violationItems + '}';
    }
}
