package com.example.demo.mapper;

import com.example.demo.entity.ItemDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Green写代码
 * @date 2023-04-21 14:00
 */
@Mapper
public interface ItemMapper {
    List<ItemDo> listItem();

    int deleteByPrimaryKey(Integer id);
    int insert(ItemDo record);
    int insertSelective(ItemDo record);
    ItemDo selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(ItemDo record);
    int updateByPrimaryKey(ItemDo record);
    int increaseSales(@Param("id")Integer id, @Param("amount")Integer amount);
}
