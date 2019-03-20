package com.hilox.seckill.service;

import com.hilox.seckill.service.model.PromoModel;

/**
 * 秒杀活动Service
 * @author Hilox
 * @date 2019-03-21 00:01
 */
public interface PromoService {

    /**
     * 获取秒杀信息
     * @param itemId
     * @return
     */
    PromoModel getPromoByItemId(Integer itemId);
}
