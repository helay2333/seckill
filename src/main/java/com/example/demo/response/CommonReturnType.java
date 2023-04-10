package com.example.demo.response;

import lombok.Data;

/**
 * @author Green写代码
 * @date 2023-04-09 14:57
 */
@Data
public class CommonReturnType {
    private String status;
    //status=success, data返回正确方式
    //为fail, 返回通用的错误码格式
    private Object data;
    //定义一个通用的创建方法
    public static CommonReturnType create(Object result) {
        return CommonReturnType.create(result, "success");
    }
    public static CommonReturnType create(Object result, String status){
        CommonReturnType type = new CommonReturnType();
        type.setStatus(status);
        type.setData(result);
        return type;
    }

}
