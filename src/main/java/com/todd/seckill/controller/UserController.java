package com.todd.seckill.controller;

import com.todd.seckill.controller.viewobject.UserVO;
import com.todd.seckill.service.UserService;
import com.todd.seckill.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户Controller
 * @author Todd
 * @date 2019-03-17 16:27
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 获取对应用户信息
     * @param id
     */
    @GetMapping("/get")
    @ResponseBody
    public UserVO getUser(@RequestParam(name = "id") Integer id) {
        UserModel userModel = userService.getUserById(id);
        return convertFromModel(userModel);
    }

    /**
     * 将领域模型用户对象转为供前端使用的视图对象
     * @param userModel
     * @return
     */
    private UserVO convertFromModel(UserModel userModel) {
        if (userModel == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userModel, userVO);

        return userVO;
    }
}
