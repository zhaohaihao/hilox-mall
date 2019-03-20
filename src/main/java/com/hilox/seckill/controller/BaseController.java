package com.hilox.seckill.controller;

import com.hilox.seckill.error.BussinessException;
import com.hilox.seckill.error.BussinessErrorEnum;
import com.hilox.seckill.response.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 基类Controller
 * @author Hilox
 * @date 2019-03-17 23:06
 */
public class BaseController {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object handlerException(HttpServletRequest request, Exception ex) {

        Map<String, Object> responseMap = new HashMap<>();
        if (ex instanceof BussinessException) {
            BussinessException bussinessException = (BussinessException) ex;
            responseMap.put("errCode", bussinessException.getErrCode());
            responseMap.put("errMsg", bussinessException.getErrMsg());
        } else {
            responseMap.put("errCode", BussinessErrorEnum.UNKNOW_ERROR.getErrCode());
            responseMap.put("errMsg", BussinessErrorEnum.UNKNOW_ERROR.getErrMsg());
        }
        return CommonResponse.create(responseMap, 1);
    }
}
