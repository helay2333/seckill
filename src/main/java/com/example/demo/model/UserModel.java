package com.example.demo.model;

import lombok.Data;

/**
 * @author Green写代码
 * @date 2023-03-18 18:26
 * 这里才是映射Java代码中的Model层, 这里是和后端代码进行映射的实体类
 * 而entity是和数据库进行映射的DAO层
 */
@Data
public class UserModel {
    private Integer id;

    private String name;

    private Byte gender;

    private Integer age;


    private String telephone;

    private String registerMode;

    private String thirdPartyId;





    private String encrptPassword;
}
