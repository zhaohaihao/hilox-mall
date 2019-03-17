package com.todd.seckill.response;

/**
 * 返回结果对象
 * @author Todd
 * @date 2019-03-17 22:23
 */
public class CommonResponse {

    /**
     * 返回处理结果, 0:成功, 1:失败
     */
    private Integer code;

    /**
     * 返回的数据
     */
    private Object data;

    public static CommonResponse create(Object result) {
        return create(result, 0);
    }

    public static CommonResponse create(Object result, Integer code) {
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setCode(code);
        commonResponse.setData(result);
        return commonResponse;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
