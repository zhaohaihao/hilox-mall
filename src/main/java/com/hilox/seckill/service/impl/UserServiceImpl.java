package com.hilox.seckill.service.impl;

import com.hilox.seckill.dao.UserDOMapper;
import com.hilox.seckill.dao.UserPasswordDOMapper;
import com.hilox.seckill.dataobject.UserDO;
import com.hilox.seckill.dataobject.UserPasswordDO;
import com.hilox.seckill.error.BussinessException;
import com.hilox.seckill.service.model.UserModel;
import com.hilox.seckill.validator.ValidationResult;
import com.hilox.seckill.validator.ValidatorImpl;
import com.hilox.seckill.error.BussinessErrorEnum;
import com.hilox.seckill.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户Service实现类
 * @author Hilox
 * @date 2019-03-17 16:30
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDOMapper userDOMapper;

    @Autowired
    private UserPasswordDOMapper userPasswordDOMapper;

    @Autowired
    private ValidatorImpl validator;

    @Override
    public UserModel getUserById(Integer id) {
        UserDO userDO = userDOMapper.selectByPrimaryKey(id);
        if (userDO == null) {
            return null;
        }
        // 获取对应用户的加密密码信息
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getId());

        return convertFromDataObject(userDO, userPasswordDO);
    }

    @Override
    @Transactional
    public void register(UserModel userModel) throws BussinessException {
        if (userModel == null) {
            throw new BussinessException(BussinessErrorEnum.PARAMETER_VALIDATION_ERROR);
        }

        // if (StringUtils.isNoneEmpty(userModel.getName())
        //     || userModel.getGender() == null
        //     || userModel.getAge() == null
        //     || StringUtils.isNoneEmpty(userModel.getTelphone())) {
        //     throw new BussinessException(BussinessErrorEnum.PARAMETER_VALIDATION_ERROR);
        // }

        ValidationResult validate = validator.validate(userModel);
        if (validate.isHasErrors()) {
            throw new BussinessException(BussinessErrorEnum.PARAMETER_VALIDATION_ERROR, validate.getErrMsg());
        }

        UserDO userDO = convertFromModel(userModel);
        try {
            userDOMapper.insertSelective(userDO);
        } catch (DuplicateKeyException e) {
            // 数据库中为telphone设置了唯一索引
            throw new BussinessException(BussinessErrorEnum.PARAMETER_VALIDATION_ERROR, "手机号已重复注册");
        }

        userModel.setId(userDO.getId());

        UserPasswordDO userPasswordDO = convertPasswordFromModel(userModel);
        userPasswordDOMapper.insertSelective(userPasswordDO);
    }

    @Override
    public UserModel validateLogin(String telphone, String encrptPassword) throws BussinessException {
        // 通过用户的手机获取用户的信息
        UserDO userDO = userDOMapper.selectByTelphone(telphone);
        if (userDO == null) {
            throw new BussinessException(BussinessErrorEnum.USER_LOGIN_FAIL);
        }
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getId());
        UserModel userModel = convertFromDataObject(userDO, userPasswordDO);

        // 比对用户信息内加密的密码是否和传输进来的密码相匹配
        if (!StringUtils.equals(encrptPassword, userModel.getEncrptPassword())) {
            throw new BussinessException(BussinessErrorEnum.USER_LOGIN_FAIL);
        }

        return userModel;
    }

    /**
     * 用户模型组装
     * @param userModel
     * @return
     */
    private UserPasswordDO convertPasswordFromModel(UserModel userModel) {
        if (userModel == null) {
            return null;
        }

        UserPasswordDO userPasswordDO = new UserPasswordDO();
        userPasswordDO.setEncrptPassword(userModel.getEncrptPassword());
        userPasswordDO.setUserId(userModel.getId());
        return userPasswordDO;
    }

    /**
     * 用户模型组装
     * @param userModel
     * @return
     */
    private UserDO convertFromModel(UserModel userModel) {
        if (userModel == null) {
            return null;
        }

        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userModel, userDO);

        return userDO;
    }

    /**
     * 用户模型组装
     * @param userDO 用户信息
     * @param userPasswordDO 用户密码信息
     * @return
     */
    private UserModel convertFromDataObject(UserDO userDO, UserPasswordDO userPasswordDO) {
        if (userDO == null) {
            return null;
        }
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDO, userModel);

        if (userPasswordDO != null) {
            userModel.setEncrptPassword(userPasswordDO.getEncrptPassword());
        }
        return userModel;
    }
}
