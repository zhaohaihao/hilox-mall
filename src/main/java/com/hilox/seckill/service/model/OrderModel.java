package com.hilox.seckill.service.model;

import java.math.BigDecimal;

/**
 * 用户下单的交易模型
 * @author Hilox
 * @date 2019-03-20 22:14
 */
public class OrderModel {

    /**
     * 交易号
     */
    private String id;

    /**
     * 购买的用户ID
     */
    private Integer userId;

    /**
     * 购买的商品ID
     */
    private Integer itemId;

    /**
     * 若非空, 则表示是以秒杀商品方式下单
     */
    private Integer promoId;

    /**
     * 购买商品的单价
     */
    private BigDecimal itemPrice;

    /**
     * 购买的数量
     */
    private Integer amount;

    /**
     * 购买的金额
     */
    private BigDecimal orderPrice;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public Integer getPromoId() {
        return promoId;
    }

    public void setPromoId(Integer promoId) {
        this.promoId = promoId;
    }
}
