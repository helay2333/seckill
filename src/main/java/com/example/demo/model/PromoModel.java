package com.example.demo.model;

import lombok.Data;
import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author Green写代码
 * @date 2023-04-21 14:24
 */
@Data
public class PromoModel {
    private Integer id;

    //秒杀活动状态 1表示还未开始，2表示进行中，3表示已结束
    private Integer status;

    //秒杀活动名称
    private String promoName;

    //秒杀活动的开始时间
    private DateTime startDate;

    //秒杀活动的结束时间
    private DateTime endDate;

    //秒杀活动的适用商品
    private Integer itemId;

    //秒杀活动的商品价格
    private BigDecimal promoItemPrice;
}
