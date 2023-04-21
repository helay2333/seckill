package com.example.demo.mapper;

import com.example.demo.entity.ItemStock;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Green写代码
 * @date 2023-04-21 14:09
 */
@Mapper
public interface ItemStockMapper {
    int deleteByPrimaryKey(Integer id);


    int insert(ItemStock record);


    int insertSelective(ItemStock record);


    ItemStock selectByPrimaryKey(Integer id);

    ItemStock selectByItemId(Integer itemId);

    int decreaseStock(@Param("itemId") Integer itemId, @Param("amount") Integer amount);

    int updateByPrimaryKeySelective(ItemStock record);


    int updateByPrimaryKey(ItemStock record);
}
