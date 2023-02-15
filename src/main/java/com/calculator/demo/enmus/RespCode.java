package com.calculator.demo.enmus;

/**
 * @Author: fangjunjie
 * @createTime: 2023年02月14日 23:16:01
 * @Description: 返回值枚举
 */
public enum RespCode {
    SUCCESS(20000, "操作成功"),
    ERROR(50000, "操作失败");

    private Integer code;
    private String message;

    RespCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
