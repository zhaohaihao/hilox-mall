package com.hilox.seckill.service.model;

import org.joda.time.DateTime;

import java.math.BigDecimal;

/**
 * 秒杀模型
 * @author Hilox
 * @date 2019-03-20 23:50
 */
public class PromoModel {

    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 秒杀活动状态, 1未开始, 2进行中, 3已结束
     */
    private Integer status;

    /**
     * 秒杀活动名称
     */
    private String promoName;

    /**
     * 秒杀活动的开始时间
     */
    private DateTime startDate;

    /**
     * 秒杀活动的结束时间
     */
    private DateTime endDate;

    /**
     * 秒杀活动的适用商品
     */
    private Integer itemId;

    /**
     * 秒杀活动的商品价格
     */
    private BigDecimal promoItemPrice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPromoName() {
        return promoName;
    }

    public void setPromoName(String promoName) {
        this.promoName = promoName;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public BigDecimal getPromoItemPrice() {
        return promoItemPrice;
    }

    public void setPromoItemPrice(BigDecimal promoItemPrice) {
        this.promoItemPrice = promoItemPrice;
    }
}
