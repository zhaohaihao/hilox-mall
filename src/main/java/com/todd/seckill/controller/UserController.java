package com.todd.seckill.controller;

import com.todd.seckill.controller.viewobject.UserVO;
import com.todd.seckill.error.BussinessErrorEnum;
import com.todd.seckill.error.BussinessException;
import com.todd.seckill.response.CommonResponse;
import com.todd.seckill.service.UserService;
import com.todd.seckill.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

/**
 * 用户Controller
 * @author Todd
 * @date 2019-03-17 16:27
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    /**
     * 获取otp短信
     * @param telphone
     * @return
     */
    @GetMapping("/getOtp")
    @ResponseBody
    public CommonResponse getOtp(@RequestParam(name = "telphone") String telphone) {
        // 按规则生成otp验证码
        Random random = new Random();
        int randomInt = random.nextInt(99999) + 10000;
        String otpCode = String.valueOf(randomInt);

        // 将otp验证码与用户手机关联
        httpServletRequest.getSession().setAttribute(telphone, randomInt);

        // 将otp验证码发送给用户, 略
        String message = "telphone: %s, optCode: %s";
        System.out.println(String.format(message, telphone, otpCode));

        return CommonResponse.create(null);
    }

    /**
     * 获取对应用户信息
     * @param id
     */
    @GetMapping("/get")
    @ResponseBody
    public CommonResponse getUser(@RequestParam(name = "id") Integer id) throws BussinessException {
        UserModel userModel = userService.getUserById(id);

        if (userModel == null) {
            throw new BussinessException(BussinessErrorEnum.USER_NOT_EXIST);
        }
        UserVO userVO = convertFromModel(userModel);
        return CommonResponse.create(userVO);
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
