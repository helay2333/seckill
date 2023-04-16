package com.example.demo.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Green写代码
 * @date 2023-04-16 14:15
 */
@Data
public class OrderModel {
    private String id;
    //购买商品单价
    private BigDecimal itemPrice;
    private Integer userId;
    //商品id
    private Integer itemId;
    //购买数量
    private Integer amount;
    //购买金额
    private BigDecimal orderAmount;

}
