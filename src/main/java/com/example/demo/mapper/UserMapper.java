package com.example.demo.mapper;

import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Green写代码
 * @date 2023-03-18 18:10
 */
@Mapper
public interface UserMapper {
    public User getUserById(Integer id);
}
