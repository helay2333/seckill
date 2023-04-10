package com.example.demo.entity;

import lombok.Data;

/**
 * @author Green写代码
 * @date 2023-03-18 18:28
 */
@Data
public class UserPassword {
    private Integer id;
    private String encrptPassword;
    private Integer userId;

}
