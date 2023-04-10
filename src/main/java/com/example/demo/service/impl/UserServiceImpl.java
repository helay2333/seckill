package com.example.demo.service.impl;

import com.example.demo.controller.viewobjet.UserVo;
import com.example.demo.entity.User;
import com.example.demo.entity.UserPassword;
import com.example.demo.error.BusinessException;
import com.example.demo.error.EmBusinessError;
import com.example.demo.mapper.UserMapper;
import com.example.demo.mapper.UserPasswordMapper;
import com.example.demo.model.UserModel;
import com.example.demo.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional
    public void register(UserModel userModel) throws BusinessException {
        if(userModel == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        if (StringUtils.isEmpty(userModel.getName())
            || userModel.getGender() == null
            || userModel.getAge() == null
            || StringUtils.isEmpty(userModel.getTelephone())
        ) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        //实现mode -> dataObject
        User user = convertFromModel(userModel);
        userMapper.insertUser(user);

        userModel.setId(user.getId());
        UserPassword userPassword = convertPasswordFromModel(userModel);
        userPasswordMapper.insertPassword(userPassword);
        return ;
    }
    private UserPassword convertPasswordFromModel(UserModel userModel){
        if(userModel == null){
            return null;
        }
        UserPassword userPassword = new UserPassword();
        userPassword.setUserId(userModel.getId());
        userPassword.setEncrptPassword(userPassword.getEncrptPassword());
        return userPassword;
    }
    private User convertFromModel(UserModel userModel) {
        if(userModel == null) {
            return null;
        }
        User user = new User();
        BeanUtils.copyProperties(userModel, user);
        return user;
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
