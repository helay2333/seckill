package com.example.demo.entity;

import lombok.Data;

/**
 * @author Green写代码
 * @date 2023-03-18 18:19
 */
@Data
public class User {

    private Integer id;

    private String name;

    private Byte gender;

    private Integer age;


    private String telephone;

    private String registerMode;

    private String thirdPartyId;
}
