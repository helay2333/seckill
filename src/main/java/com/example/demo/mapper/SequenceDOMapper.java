package com.example.demo.mapper;

import com.example.demo.entity.SequenceDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SequenceDOMapper {

    int deleteByPrimaryKey(String name);

    int insert(SequenceDO record);

    int insertSelective(SequenceDO record);

    SequenceDO selectByPrimaryKey(String name);

    SequenceDO getSequenceByName(String name);

    int updateByPrimaryKeySelective(SequenceDO record);

    int updateByPrimaryKey(SequenceDO record);
}