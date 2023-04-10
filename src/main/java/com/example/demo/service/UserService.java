package com.example.demo.service;

import com.example.demo.error.BusinessException;
import com.example.demo.model.UserModel;

/**
 * @author Green写代码
 * @date 2023-03-18 18:08
 */
public interface UserService {
    UserModel getUserById(Integer id);
    void register(UserModel userModel) throws BusinessException;
}
