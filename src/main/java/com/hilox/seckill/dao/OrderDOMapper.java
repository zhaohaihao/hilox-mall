package com.hilox.seckill.dao;

import com.hilox.seckill.dataobject.OrderDO;

public interface OrderDOMapper {
    int deleteByPrimaryKey(String id);

    int insert(OrderDO record);

    int insertSelective(OrderDO record);

    OrderDO selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OrderDO record);

    int updateByPrimaryKey(OrderDO record);
}