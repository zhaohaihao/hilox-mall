package com.hilox.seckill.service.impl;

import com.hilox.seckill.dao.OrderDOMapper;
import com.hilox.seckill.dao.SequenceDOMapper;
import com.hilox.seckill.dataobject.OrderDO;
import com.hilox.seckill.dataobject.SequenceDO;
import com.hilox.seckill.error.BussinessErrorEnum;
import com.hilox.seckill.error.BussinessException;
import com.hilox.seckill.service.ItemService;
import com.hilox.seckill.service.OrderService;
import com.hilox.seckill.service.UserService;
import com.hilox.seckill.service.model.ItemModel;
import com.hilox.seckill.service.model.OrderModel;
import com.hilox.seckill.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 下单Service实现类
 * @author Hilox
 * @date 2019-03-20 22:29
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderDOMapper orderDOMapper;

    @Autowired
    private SequenceDOMapper sequenceDOMapper;

    @Override
    @Transactional
    public OrderModel createModel(Integer userId, Integer itemId, Integer amount) throws BussinessException {

        ItemModel itemModel = itemService.getItemById(itemId);
        if (itemModel == null) {
            throw new BussinessException(BussinessErrorEnum.PARAMETER_VALIDATION_ERROR, "商品信息不存在");
        }

        UserModel userModel = userService.getUserById(userId);
        if (userModel == null) {
            throw new BussinessException(BussinessErrorEnum.PARAMETER_VALIDATION_ERROR, "用户信息不存在");
        }

        if (amount <= 0 || amount >= 99) {
            throw new BussinessException(BussinessErrorEnum.PARAMETER_VALIDATION_ERROR, "数量信息不正确");
        }

        // 落单减库存
        boolean result = itemService.decreaseStock(itemId, amount);

        if (!result) {
            throw new BussinessException(BussinessErrorEnum.STOCK_NOT_ENOUGH);
        }

        // 订单入库
        OrderModel orderModel = new OrderModel();
        orderModel.setUserId(userId);
        orderModel.setItemId(itemId);
        orderModel.setAmount(amount);
        orderModel.setItemPrice(itemModel.getPrice());
        orderModel.setOrderPrice(itemModel.getPrice().multiply(new BigDecimal(amount)));
        // 生成交易流水号
        orderModel.setId(generateOrderNo());

        OrderDO orderDO = convertFromOrderModel(orderModel);
        orderDOMapper.insertSelective(orderDO);

        // 加上商品的销量
        itemService.increaseSales(itemId, amount);

        return orderModel;
    }

    /**
     * 订单号生成规则
     *
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private String generateOrderNo() {
        // 订单号共有16位
        StringBuilder stringBuilder = new StringBuilder();
        // 前8位为时间信息, 年月日
        LocalDateTime now = LocalDateTime.now();
        String nowDate = now.format(DateTimeFormatter.ISO_DATE).replace("-", "");
        stringBuilder.append(nowDate);
        // 中间6位为自增序列
        SequenceDO sequenceDO = sequenceDOMapper.getSequenceByName("order_info");
        Integer sequence = sequenceDO.getCurrentValue();
        sequenceDO.setCurrentValue(sequence + sequenceDO.getStep());
        sequenceDOMapper.updateByPrimaryKeySelective(sequenceDO);
        String sequenceStr = String.valueOf(sequence);
        for (int i = 0; i < 6 - sequenceStr.length(); i++) {
            stringBuilder.append(0);
        }
        stringBuilder.append(sequenceStr);
        // 最后2位为分库分表位, 暂时先写死
        stringBuilder.append("00");

        return stringBuilder.toString();
    }

    /**
     * 模型转换
     *
     * @param orderModel
     * @return
     */
    private OrderDO convertFromOrderModel(OrderModel orderModel) {
        if (orderModel == null) {
            return null;
        }

        OrderDO orderDO = new OrderDO();
        BeanUtils.copyProperties(orderModel, orderDO);
        orderDO.setItemPrice(orderModel.getItemPrice().doubleValue());
        orderDO.setOrderPrice(orderModel.getItemPrice().doubleValue());
        return orderDO;
    }
}
