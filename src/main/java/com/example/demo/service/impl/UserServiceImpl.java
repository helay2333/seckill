package com.example.demo.service.impl;

import com.example.demo.entity.User;
import com.example.demo.entity.UserPassword;
import com.example.demo.mapper.UserMapper;
import com.example.demo.mapper.UserPasswordMapper;
import com.example.demo.model.UserModel;
import com.example.demo.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Green写代码
 * @date 2023-03-18 18:09
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserPasswordMapper userPasswordMapper;

    @Override
    public UserModel getUserById(Integer id) {
        User user = userMapper.getUserById(id);
        if(user == null){
            return null;
        }
        //通过用户id获取对应的用户加密密码信息
        UserPassword userPassword = userPasswordMapper.selectByUserId(user.getId());

        return converFromDataObject(user, userPassword);
    }
    private UserModel converFromDataObject(User user, UserPassword userPassword){
        if(user == null) return null;
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(user, userModel);
        if(userPassword != null) {
            //保护
            userModel.setEncrptPassword(userPassword.getEncrptPassword());
        }
        return userModel;
    }
}
