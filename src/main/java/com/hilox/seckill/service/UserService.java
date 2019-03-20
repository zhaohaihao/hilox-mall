package com.hilox.seckill.service;

import com.hilox.seckill.error.BussinessException;
import com.hilox.seckill.service.model.UserModel;

/**
 * 用户Service
 * @author Hilox
 * @date 2019-03-17 16:29
 */
public interface UserService {

    /**
     * 通过用户ID获取用户信息
     * @param id
     */
    UserModel getUserById(Integer id);

    /**
     * 用户注册
     * @param userModel
     */
    void register(UserModel userModel) throws BussinessException;

    /**
     * 校验登录
     * @param telphone
     * @param encrptPassword
     */
    UserModel validateLogin(String telphone, String encrptPassword) throws BussinessException;
}
