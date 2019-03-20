package com.hilox.seckill.controller;

import com.hilox.seckill.error.BussinessErrorEnum;
import com.hilox.seckill.error.BussinessException;
import com.hilox.seckill.response.CommonResponse;
import com.hilox.seckill.service.OrderService;
import com.hilox.seckill.service.model.OrderModel;
import com.hilox.seckill.service.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * 下单Controller
 * @author Hilox
 * @date 2019-03-20 23:17
 */
@Controller
@RequestMapping("/order")
@CrossOrigin(origins = {"*"}, allowedHeaders = "true")
public class OrderController extends BaseController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    /**
     * 用户下单
     * @param itemId
     * @param amount
     * @return
     */
    public CommonResponse createOrder(@RequestParam(name = "itemId") Integer itemId,
                                      @RequestParam(name = "amount") Integer amount,
                                      @RequestParam(name = "promoId", required = false) Integer promoId) throws BussinessException {
        Boolean isLogin = (Boolean) httpServletRequest.getSession().getAttribute("IS_LOGIN");
        if (isLogin == null | !isLogin.booleanValue()) {
            throw new BussinessException(BussinessErrorEnum.USER_NOT_LOGIN, "用户未登陆, 不能下单");
        }

        UserModel userModel = (UserModel) httpServletRequest.getSession().getAttribute("LOGIN_USER");

        OrderModel orderModel = orderService.createModel(userModel.getId(), itemId, promoId, amount);

        return CommonResponse.create(null);
    }
}
