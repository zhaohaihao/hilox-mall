package com.hilox.seckill.service;

import com.hilox.seckill.error.BussinessException;
import com.hilox.seckill.service.model.ItemModel;

import java.util.List;

/**
 * 商品Service
 * @author Hilox
 * @date 2019-03-20 21:21
 */
public interface ItemService {

    /**
     * 创建商品
     * @param itemModel
     * @return
     */
    ItemModel createItem(ItemModel itemModel) throws BussinessException;

    /**
     * 商品列表浏览
     * @return
     */
    List<ItemModel> listItem();

    /**
     * 商品详情浏览
     * @param id
     * @return
     */
    ItemModel getItemById(Integer id);

    /**
     * 减库存
     * @param itemId
     * @param amount
     * @return
     * @throws BussinessException
     */
    boolean decreaseStock(Integer itemId, Integer amount) throws BussinessException;

    /**
     * 商品销量增加
     */
    void increaseSales(Integer itemId, Integer amount) throws BussinessException;
}
