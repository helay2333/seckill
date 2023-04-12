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
import com.example.demo.validator.ValidataionResult;
import com.example.demo.validator.ValidatorImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.DuplicateFormatFlagsException;

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

    @Autowired
    private ValidatorImpl validator;



    @Override
    @Transactional//处理事务
    public void register(UserModel userModel) throws BusinessException {
        if(userModel == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
//        if (StringUtils.isEmpty(userModel.getName())
//            || userModel.getGender() == null
//            || userModel.getAge() == null
//            || StringUtils.isEmpty(userModel.getTelephone())
//        ) {
//            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
//        }
        ValidataionResult result = validator.validate(userModel);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }

        //实现mode -> dataObject
        User user = convertFromModel(userModel);
        //防止两个手机号一模一样的注册, 因为我们设置了不可重复键telephone
        try{
            userMapper.insertUser(user);
        }catch (DuplicateFormatFlagsException exception){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "手机号已经重复注册")
        ;}

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

    public UserModel validateLogin(String telephone, String encrptPassword) throws BusinessException {
        //通过用户的手机获取用户信息
        User userDO = userMapper.selectByTelphone(telephone);
        if(userDO == null){
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }
        UserPassword userPasswordDO = userPasswordMapper.selectByUserId(userDO.getId());
        UserModel userModel = convertFromDataObject(userDO,userPasswordDO);

        //比对用户信息内加密的密码是否和传输进来的密码相匹配
        if(!StringUtils.equals(encrptPassword,userModel.getEncrptPassword())){
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }
        return userModel;
    }
    private UserModel convertFromDataObject(User userDO, UserPassword userPasswordDO){
        if(userDO == null){
            return null;
        }
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDO,userModel);

        if(userPasswordDO != null){
            userModel.setEncrptPassword(userPasswordDO.getEncrptPassword());
        }

        return userModel;
    }
}
