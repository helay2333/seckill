package com.example.demo.entity;

import lombok.Data;

/**
 * @author Green写代码
 * @date 2023-04-21 14:55
 */
@Data
public class OrderDO {

    private String id;

    private Integer userId;

    private Integer itemId;

    private Double itemPrice;

    private Integer amount;

    private Double orderPrice;

    private Integer promoId;
}
