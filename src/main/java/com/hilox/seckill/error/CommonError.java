package com.hilox.seckill.error;

/**
 * 返回错误信息
 * @author Hilox
 * @date 2019-03-17 22:34
 */
public interface CommonError {

    int getErrCode();

    String getErrMsg();

    CommonError setErrMsg(String errMsg);
}
