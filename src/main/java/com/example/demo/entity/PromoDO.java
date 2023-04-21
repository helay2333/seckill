package com.example.demo.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author Green写代码
 * @date 2023-04-21 14:34
 */
@Data
public class PromoDO {

    private Integer id;


    private String promoName;


    private Date startDate;

    private Date endDate;

    private Integer itemId;

    private Double promoItemPrice;

}
