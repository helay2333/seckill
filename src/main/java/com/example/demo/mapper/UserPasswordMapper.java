package com.example.demo.mapper;

import com.example.demo.entity.UserPassword;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserPasswordMapper {
    public UserPassword selectByUserId(Integer id);
    public void insertPassword(UserPassword userPassword);
}
