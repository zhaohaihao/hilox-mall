package com.hilox.seckill.service.impl;

import com.hilox.seckill.dao.ItemDOMapper;
import com.hilox.seckill.dao.ItemStockDOMapper;
import com.hilox.seckill.dataobject.ItemDO;
import com.hilox.seckill.dataobject.ItemStockDO;
import com.hilox.seckill.error.BussinessErrorEnum;
import com.hilox.seckill.error.BussinessException;
import com.hilox.seckill.service.ItemService;
import com.hilox.seckill.service.model.ItemModel;
import com.hilox.seckill.validator.ValidationResult;
import com.hilox.seckill.validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品Service实现类
 * @author Hilox
 * @date 2019-03-20 21:24
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ValidatorImpl validator;

    @Autowired
    private ItemDOMapper itemDOMapper;

    @Autowired
    private ItemStockDOMapper itemStockDOMapper;

    @Override
    @Transactional
    public ItemModel createItem(ItemModel itemModel) throws BussinessException {

        ValidationResult validationResult = validator.validate(itemModel);
        if(validationResult.isHasErrors()) {
            throw new BussinessException(BussinessErrorEnum.PARAMETER_VALIDATION_ERROR, validationResult.getErrMsg());
        }

        ItemDO itemDO = convertItemDOFromItemModel(itemModel);

        itemDOMapper.insertSelective(itemDO);
        itemModel.setId(itemDO.getId());

        ItemStockDO itemStockDO = convertItemStockDOFromItemModel(itemModel);
        itemStockDOMapper.insertSelective(itemStockDO);

        return getItemById(itemModel.getId());
    }

    @Override
    public List<ItemModel> listItem() {
        List<ItemDO> itemDOList = itemDOMapper.listItem();
        List<ItemModel> itemModelList = itemDOList.stream().map(itemDO -> {
            ItemStockDO itemStockDO = itemStockDOMapper.selectByItemId(itemDO.getId());
            ItemModel itemModel = convertModelFromDataObject(itemDO, itemStockDO);
            return itemModel;
        }).collect(Collectors.toList());

        return itemModelList;
    }


    @Override
    public ItemModel getItemById(Integer id) {
        ItemDO itemDO = itemDOMapper.selectByPrimaryKey(id);
        if (itemDO == null) {
            return null;
        }

        ItemStockDO itemStockDO = itemStockDOMapper.selectByItemId(itemDO.getId());
        ItemModel itemModel = convertModelFromDataObject(itemDO, itemStockDO);

        return itemModel;
    }

    @Override
    public boolean decreaseStock(Integer itemId, Integer amount) throws BussinessException {
        int affectedRow = itemStockDOMapper.decreaseStock(itemId, amount);
        if (affectedRow > 0) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public void increaseSales(Integer itemId, Integer amount) throws BussinessException {
        itemDOMapper.increaseSales(itemId, amount);
    }

    /**
     * 模型转换
     * @param itemDO
     * @param itemStockDO
     * @return
     */
    private ItemModel convertModelFromDataObject(ItemDO itemDO, ItemStockDO itemStockDO) {

        ItemModel itemModel = new ItemModel();
        BeanUtils.copyProperties(itemDO, itemModel);
        itemModel.setPrice(new BigDecimal(itemDO.getPrice()));
        itemModel.setStock(itemStockDO.getStock());

        return itemModel;
    }

    /**
     * 模型装换
     * @param itemModel
     * @return
     */
    private ItemDO convertItemDOFromItemModel(ItemModel itemModel) {
        if (itemModel == null) {
            return null;
        }

        ItemDO itemDO = new ItemDO();
        BeanUtils.copyProperties(itemModel, itemDO);
        itemDO.setPrice(itemModel.getPrice().doubleValue());
        return itemDO;
    }

    /**
     * 模型转换
     * @param itemModel
     * @return
     */
    private ItemStockDO convertItemStockDOFromItemModel(ItemModel itemModel) {
        if (itemModel == null) {
            return null;
        }

        ItemStockDO itemStockDO = new ItemStockDO();
        itemStockDO.setItemId(itemModel.getId());
        itemStockDO.setStock(itemModel.getStock());
        return itemStockDO;
    }
}
