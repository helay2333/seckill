package com.example.demo.validator;



import jdk.internal.org.xml.sax.SAXException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.util.Set;


/**
 * @author Green写代码
 * @date 2023-04-12 13:36
 */
@Component
public class ValidatorImpl implements InitializingBean {
    //实现校验方法并返回校验结果
    public ValidataionResult validate(Object bean){
        final ValidataionResult result = new ValidataionResult();
        Set<ConstraintViolation<Object>> constraintViolationSet = validator.validate(bean);
        if(constraintViolationSet.size() > 0){
            //有错误
            result.setHasErrors(true);
            constraintViolationSet.forEach(constraintViolation->{
                String errMsg = constraintViolation.getMessage();
                String propertyName = constraintViolation.getPropertyPath().toString();
                result.getErrorMsgMap().put(propertyName,errMsg);
            });
        }
        return result;
    }


    private Validator validator;
    //springbean初始化之后会回调调用这个方法
    @Override
    public void afterPropertiesSet() throws Exception {
        //将hibernate validator通过工厂的初始化方式使其实例化
        this.validator = (Validator) Validation.buildDefaultValidatorFactory().getValidator();
    }
}
