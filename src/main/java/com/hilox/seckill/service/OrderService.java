package com.hilox.seckill.service;

import com.hilox.seckill.error.BussinessException;
import com.hilox.seckill.service.model.OrderModel;

/**
 * 订单交易Service
 * @author Hilox
 * @date 2019-03-20 22:27
 */
public interface OrderService {

    /**
     * 下单
     * @param userId
     * @param itemId
     * @param amount
     * @return
     */
    OrderModel createModel(Integer userId, Integer itemId, Integer amount) throws BussinessException;
}
