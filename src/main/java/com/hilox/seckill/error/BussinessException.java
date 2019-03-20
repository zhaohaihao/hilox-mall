package com.hilox.seckill.error;

/**
 * 业务异常
 * @author Hilox
 * @date 2019-03-17 22:44
 */
public class BussinessException extends Exception implements CommonError {

    private CommonError commonError;

    public BussinessException(CommonError commonError) {
        super();
        this.commonError = commonError;
    }

    public BussinessException(CommonError commonError, String errMsg) {
        super();
        this.commonError = commonError;
        this.commonError.setErrMsg(errMsg);
    }

    @Override
    public int getErrCode() {
        return this.commonError.getErrCode();
    }

    @Override
    public String getErrMsg() {
        return this.commonError.getErrMsg();
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.commonError.setErrMsg(errMsg);
        return this;
    }
}
