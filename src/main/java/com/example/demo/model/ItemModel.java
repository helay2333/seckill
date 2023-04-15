package com.example.demo.model;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author Green写代码
 * @date 2023-04-15 12:34
 */
@Data
public class ItemModel {
    private Integer id;

    @NotBlank(message = "商品名不能为空")
    //商品名称
    private String title;
    //商品价格
    @NotNull(message = "价格不能为空")
    @Min(value = 0, message = "商品价格必须>0")
    private BigDecimal price;
    //商品库存
    @NotNull(message = "库存不能不填")
    private Integer stock;//在数据库中拆到另一张表中, 利于后续进行优化
    //商品描述
    @NotBlank(message = "商品描述信息不能为空")
    private String description;
    //商品销量
    private Integer sales;//暂时放在item中, 在用户购买时通过异步方式改变此值
    //商品url图片
    @NotBlank(message = "商品图片信息不能为空")
    private String imgUrl;


}
