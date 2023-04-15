package com.example.demo.service.impl;

import com.example.demo.error.BusinessException;
import com.example.demo.error.EmBusinessError;
import com.example.demo.model.ItemModel;
import com.example.demo.service.ItemService;
import com.example.demo.validator.ValidataionResult;
import com.example.demo.validator.ValidatorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Green写代码
 * @date 2023-04-15 12:56
 */
public class ItemServiceImp implements ItemService {
    @Autowired
    private ValidatorImpl validator;

    @Override
    @Transactional
    public ItemModel createItem(ItemModel itemModel) {
        //校验入参
        ValidataionResult validataionResult = validator.validate(itemModel);
        if(validataionResult.isHasErrors()) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,validataionResult.getErrMsg())
        }
        //转化itemmodel->dataobject;


        //写入数据库

        //返回创建完成的对象
        return null;
    }


    @Override
    public List<ItemModel> listItem() {
        return null;
    }

    @Override
    public ItemModel getItemByI(Integer id) {
        return null;
    }
}
