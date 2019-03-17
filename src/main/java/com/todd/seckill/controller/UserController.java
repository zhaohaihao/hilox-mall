package com.todd.seckill.controller;

import com.alibaba.druid.util.StringUtils;
import com.todd.seckill.controller.viewobject.UserVO;
import com.todd.seckill.error.BussinessErrorEnum;
import com.todd.seckill.error.BussinessException;
import com.todd.seckill.response.CommonResponse;
import com.todd.seckill.service.UserService;
import com.todd.seckill.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.util.Random;

/**
 * 用户Controller
 * @author Todd
 * @date 2019-03-17 16:27
 */
@Controller
@RequestMapping("/user")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @PostMapping("/login")
    @ResponseBody
    public CommonResponse login(@RequestParam(name = "telphone") String telphone,
                                @RequestParam(name = "password") String password) throws Exception {
        // 入参校验
        if (StringUtils.isEmpty(telphone)
            || StringUtils.isEmpty(password)) {
            throw new BussinessException(BussinessErrorEnum.PARAMETER_VALIDATION_ERROR);
        }

        // 用户登录
        UserModel userModel = userService.validateLogin(telphone, encodeByMD5(password));

        // 将登录凭证加入到用户登录成功的session内
        this.httpServletRequest.getSession().setAttribute("IS_LOGIN", true);
        this.httpServletRequest.getSession().setAttribute("LOGIN_USER", userModel);

        return CommonResponse.create(null);
    }

    @PostMapping("/register")
    @ResponseBody
    public CommonResponse register(@RequestParam(name = "telphone") String telphone,
                                   @RequestParam(name = "otpCode") String otpCode,
                                   @RequestParam(name = "name") String name,
                                   @RequestParam(name = "gender") Integer gender,
                                   @RequestParam(name = "age") Integer age,
                                   @RequestParam(name = "password") String password) throws Exception {

        // 验证手机对应的otpCode是否符合
        String inSessionOtpCode = (String) this.httpServletRequest.getAttribute(telphone);
        if (StringUtils.equals(otpCode, inSessionOtpCode)) {
            throw new BussinessException(BussinessErrorEnum.PARAMETER_VALIDATION_ERROR, "短信验证码不符合");
        }

        // 用户注册
        UserModel userModel = new UserModel();
        userModel.setName(name);
        userModel.setGender(new Byte(String.valueOf(gender)));
        userModel.setAge(age);
        userModel.setTelphone(telphone);
        userModel.setRegisterMode("byPhone");
        userModel.setEncrptPassword(encodeByMD5(password));

        userService.register(userModel);
        return CommonResponse.create(null);
    }

    /**
     * MD5加密
     * @param password
     * @return
     * @throws Exception
     */
    public String encodeByMD5(String password) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64Encoder = new BASE64Encoder();
        String newPassword = base64Encoder.encode(md5.digest(password.getBytes("UTF-8")));
        return password;
    }

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
