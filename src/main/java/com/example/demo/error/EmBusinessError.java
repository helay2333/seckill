package com.example.demo.error;

/**
 * @author Green写代码
 * @date 2023-04-09 15:12
 */
public enum EmBusinessError implements CommonError{
    //通用的错误类型00001
    PARAMETER_VALIDATION_ERROR(10001, "参数不合适"),
    UNKNOWN_ERROR(10002, "未知错误"),

    //1开头的都是用户信息错误定义
    USER_NOT_EXITS(20001,"用户不存在"),
    USER_LOGIN_FAIL(20002,"用户手机号或者密码不存在")
    ;

    EmBusinessError(int errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    private int errCode;
    private String errMsg;
    @Override
    public int getErrCode() {
        return this.errCode;
    }

    @Override
    public String getErrMsg() {
        return this.errMsg;
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }
}
