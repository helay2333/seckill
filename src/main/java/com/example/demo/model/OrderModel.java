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
    //购买商品的单价,若promoId非空，则表示秒杀商品价格
    private BigDecimal itemPrice;
    private Integer userId;
    //商品id
    private Integer itemId;
    //若非空，则表示是以秒杀商品方式下单
    private Integer promoId;

    //购买数量
    private Integer amount;

    //购买金额,若promoId非空，则表示秒杀商品价格
    private BigDecimal orderPrice;

}
