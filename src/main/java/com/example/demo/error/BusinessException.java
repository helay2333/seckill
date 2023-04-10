package com.example.demo.error;

/**
 * @author Green写代码
 * @date 2023-04-09 15:16
 */
//包装器业务类异常实现
public class BusinessException extends Exception implements CommonError{

    private CommonError commonError;

    public BusinessException(CommonError commonError){
        super();//接受EmBusinessError传参用于构造业务异常
        this.commonError = commonError;
    }
    //接受自定义errMsg的方式构造业务异常
    public BusinessException(CommonError commonError, String errMsg){
        super();
        this.commonError = commonError;
        this.commonError.setErrMsg(errMsg);
    }
    @Override
    public int getErrCode() {
        return this.commonError.getErrCode();
    }

    @Override
    public String getErrMsg() {
        return this.commonError.getErrMsg();
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.commonError.setErrMsg(errMsg);
        return this;
    }
}
