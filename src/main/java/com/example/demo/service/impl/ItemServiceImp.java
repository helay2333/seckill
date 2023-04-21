package com.example.demo.service.impl;

import com.example.demo.entity.ItemDo;
import com.example.demo.entity.ItemStock;
import com.example.demo.error.BusinessException;
import com.example.demo.error.EmBusinessError;
import com.example.demo.mapper.ItemMapper;
import com.example.demo.mapper.ItemStockMapper;
import com.example.demo.model.ItemModel;
import com.example.demo.model.PromoModel;
import com.example.demo.service.ItemService;
import com.example.demo.service.PromoService;
import com.example.demo.validator.ValidataionResult;
import com.example.demo.validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Green写代码
 * @date 2023-04-15 12:56
 */
public class ItemServiceImp implements ItemService {
    @Autowired
    private ValidatorImpl validator;
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private ItemStockMapper itemStockMapper;
    private ItemDo convertItemDOFromItemModel(ItemModel itemModel){
        if(itemModel == null){
            return null;
        }
        ItemDo itemDO = new ItemDo();
        BeanUtils.copyProperties(itemModel,itemDO);
        itemDO.setPrice(itemModel.getPrice().doubleValue());
        return itemDO;
    }

    @Override
    @Transactional
    public ItemModel createItem(ItemModel itemModel) throws BusinessException {
        //校验入参
        ValidataionResult validataionResult = validator.validate(itemModel);
        if(validataionResult.isHasErrors()) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,validataionResult.getErrMsg());
        }
        //转化itemmodel->dataobject;
        ItemDo itemDO = this.convertItemDOFromItemModel(itemModel);
        //写入数据库
        itemMapper.insertSelective(itemDO);
        itemModel.setId(itemDO.getId());
        ItemStock itemStock = this.convertItemStockDOFromItemModel(itemModel);
        itemStockMapper.insertSelective(itemStock);
        //返回创建完成的对象
        return this.getItemById(itemModel.getId());
    }

    @Override
    public List<ItemModel> listItem() {
        List<ItemDo> itemDOList = itemMapper.listItem();
        List<ItemModel> itemModelList =  itemDOList.stream().map(itemDO -> {
            ItemStock itemStockDO = itemStockMapper.selectByItemId(itemDO.getId());
            ItemModel itemModel = this.convertModelFromDataObject(itemDO,itemStockDO);
            return itemModel;
        }).collect(Collectors.toList());
        return itemModelList;
    }

    private ItemStock convertItemStockDOFromItemModel(ItemModel itemModel){
        if(itemModel == null){
            return null;
        }
        ItemStock itemStockDO = new ItemStock();
        itemStockDO.setItemId(itemModel.getId());
        itemStockDO.setStock(itemModel.getStock());
        return itemStockDO;
    }
    private ItemModel convertModelFromDataObject(ItemDo itemDO,ItemStock itemStockDO){
        ItemModel itemModel = new ItemModel();
        BeanUtils.copyProperties(itemDO,itemModel);
        itemModel.setPrice(new BigDecimal(itemDO.getPrice()));
        itemModel.setStock(itemStockDO.getStock());

        return itemModel;
    }
    @Autowired
    private PromoService promoService;

    @Override
    public ItemModel getItemById(Integer id) {

        ItemDo itemDO = itemMapper.selectByPrimaryKey(id);
        if(itemDO == null){
            return null;
        }
        //操作获得库存数量
        ItemStock itemStockDO = itemStockMapper.selectByItemId(itemDO.getId());


        //将dataobject->model
        ItemModel itemModel = convertModelFromDataObject(itemDO,itemStockDO);

        //获取活动商品信息

        PromoModel promoModel = promoService.getPromoByItemId(itemModel.getId());
        if(promoModel != null && promoModel.getStatus().intValue() != 3){
            itemModel.setPromoModel(promoModel);
        }
        return itemModel;
    }
    @Override
    @Transactional
    public boolean decreaseStock(Integer itemId, Integer amount) throws BusinessException {
        int affectedRow =  itemStockMapper.decreaseStock(itemId,amount);
        if(affectedRow > 0){
            //更新库存成功
            return true;
        }else{
            //更新库存失败
            return false;
        }

    }
    @Override
    @Transactional
    public void increaseSales(Integer itemId, Integer amount) throws BusinessException {
        itemMapper.increaseSales(itemId,amount);
    }


}
