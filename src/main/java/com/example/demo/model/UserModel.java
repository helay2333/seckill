package com.example.demo.model;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Green写代码
 * @date 2023-03-18 18:26
 * 这里才是映射Java代码中的Model层, 这里是和后端代码进行映射的实体类
 * 而entity是和数据库进行映射的DAO层
 */
@Data
public class UserModel {
    private Integer id;
    @NotBlank(message = "用户名不能为空")
    private String name;
    @NotNull(message = "性别不能不填")
    private Byte gender;
    @NotNull(message = "年龄不能不填写")
    @Min(value = 0,message = "年龄必须大于0岁")
    @Max(value = 150,message = "年龄必须小于150岁")
    private Integer age;

    @NotBlank(message = "手机号不能为空")
    private String telephone;

    private String registerMode;

    private String thirdPartyId;
    
    @NotBlank(message = "密码不能为空")
    private String encrptPassword;
}
