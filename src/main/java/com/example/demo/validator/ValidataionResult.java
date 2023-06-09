package com.example.demo.validator;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Green写代码
 * @date 2023-04-12 13:32
 */
public class ValidataionResult {
    private boolean hasErrors = false;
    //存放错误信息的map
    private Map<String, String> errorMsg = new HashMap<>();

    public boolean isHasErrors() {
        return hasErrors;
    }
    public void setHasErrors(boolean hasErrors){
        this.hasErrors = hasErrors;
    }

    public Map<String, String> getErrorMsgMap() {
        return errorMsg;
    }

    public Map<String, String> getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(Map<String, String> errorMsg) {
        this.errorMsg = errorMsg;
    }
    //实现通用的格式化字符串信息获取错误结果的msg方法
    public String getErrMsg() {
       return StringUtils.join(errorMsg.values().toArray(), ",");
    }
}
