package com.example.demo.service;

import com.example.demo.model.ItemModel;

import java.util.List;

/**
 * @author Green写代码
 * @date 2023-04-15 12:54
 */
public interface ItemService {
    //创建商品
    ItemModel createItem(ItemModel itemModel);
    //商品列表浏览
    List<ItemModel> listItem();
    //商品详情浏览
    ItemModel getItemByI(Integer id);
}
