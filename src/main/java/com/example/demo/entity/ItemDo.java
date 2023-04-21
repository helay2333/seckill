package com.example.demo.entity;

import lombok.Data;

/**
 * @author Green写代码
 * @date 2023-04-21 13:54
 */
@Data
public class ItemDo {
    private Integer id;

    private String title;

    private Double price;

    private String description;

    private Integer sales;

    private String imgUrl;
}
