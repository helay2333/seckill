package com.example.demo.controller.viewobjet;

import lombok.Data;

/**
 * @author Green写代码
 * @date 2023-04-09 14:23
 * 这是给前端的
 */
@Data
public class UserVo {
    private Integer id;
    private String name;
    private Byte gender;
    private Integer age;
    private String telephone;
}
