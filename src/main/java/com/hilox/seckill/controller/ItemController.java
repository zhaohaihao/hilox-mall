package com.hilox.seckill.controller;

import com.hilox.seckill.controller.viewobject.ItemVO;
import com.hilox.seckill.error.BussinessException;
import com.hilox.seckill.response.CommonResponse;
import com.hilox.seckill.service.ItemService;
import com.hilox.seckill.service.model.ItemModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品Controller
 * @author Hilox
 * @date 2019-03-20 21:47
 */
@Controller
@RequestMapping("/item")
@CrossOrigin(origins = {"*"}, allowedHeaders = "true")
public class ItemController extends BaseController {

    @Autowired
    private ItemService itemService;

    /**
     * 创建商品
     * @param title
     * @param description
     * @param price
     * @param stock
     * @param imgUrl
     * @return
     */
    @PostMapping("/createItem")
    @ResponseBody
    public CommonResponse createItem(@RequestParam(name = "title") String title,
                                     @RequestParam(name = "description") String description,
                                     @RequestParam(name = "price") BigDecimal price,
                                     @RequestParam(name = "stock") Integer stock,
                                     @RequestParam(name = "imgUrl") String imgUrl) throws BussinessException {
        ItemModel itemModel = new ItemModel();
        itemModel.setTitle(title);
        itemModel.setDescription(description);
        itemModel.setPrice(price);
        itemModel.setStock(stock);
        itemModel.setImgUrl(imgUrl);

        ItemModel item = itemService.createItem(itemModel);
        ItemVO itemVO = new ItemVO();
        BeanUtils.copyProperties(item, itemVO);

        return CommonResponse.create(itemVO);
    }

    /**
     * 商品列表
     * @return
     */
    @GetMapping("/list")
    @ResponseBody
    public CommonResponse listItem() {
        List<ItemModel> itemModelList = itemService.listItem();
        List<ItemVO> collect = itemModelList.stream().map(itemModel -> {
            ItemVO itemVO = new ItemVO();
            BeanUtils.copyProperties(itemModel, itemVO);
            return itemVO;
        }).collect(Collectors.toList());
        return CommonResponse.create(collect);
    }

    /**
     * 商品详情页浏览
     * @return
     */
    @GetMapping("/getItem")
    @ResponseBody
    public CommonResponse getItem(@RequestParam(name = "id") Integer id) {
        ItemModel itemModel = itemService.getItemById(id);
        ItemVO itemVO = new ItemVO();
        BeanUtils.copyProperties(itemModel, itemVO);
        return CommonResponse.create(itemVO);
    }
}
