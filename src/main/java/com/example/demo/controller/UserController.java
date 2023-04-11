package com.example.demo.controller;

import com.example.demo.controller.viewobjet.UserVo;
import com.example.demo.entity.User;
import com.example.demo.error.BusinessException;
import com.example.demo.error.EmBusinessError;
import com.example.demo.model.UserModel;
import com.example.demo.response.CommonReturnType;
import com.example.demo.service.UserService;
import com.example.demo.service.impl.UserServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author Green写代码
 * @date 2023-03-18 18:06
 */
@RestController("/user")
@RequestMapping("/user")
/*
* allowCredentials属性:配置是否允许发送Cookie,用于凭证请求, 默认不发送cookie。 allowedHeaders属性:配置允许的自定义请求头,用于预检请求*/


@CrossOrigin(originPatterns = "*", allowCredentials = "true")//跨域处理
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @Autowired

    //拥有ThreadLocal的map让用户在每一个request处理自己的session等变量
    private HttpServletRequest httpServletRequest;

    //用户注册接口
    @RequestMapping(value = "/register",method = {RequestMethod.POST})
    @ResponseBody
    public CommonReturnType register(@RequestParam(value = "telephone") String telephone,
                                     @RequestParam(value = "otpCode")String otpCode,
                                     @RequestParam(value = "name")String name,
                                     @RequestParam(value = "gender") Integer gender,
                                     @RequestParam(name="age")Integer age,
                                     @RequestParam(name="password")String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        System.out.println(this.httpServletRequest.getSession());
        //验证otp和手机号码相符合
        String inSessionCode = (String) this.httpServletRequest.getSession().getAttribute(telephone);
        if(!otpCode.equals(inSessionCode)) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"短信验证码不符合");
        }
        //进入注册流程
        UserModel userModel = new UserModel();
        userModel.setName(name);
        userModel.setGender(new Byte(String.valueOf(gender.intValue())));
        userModel.setAge(age);
        userModel.setTelephone(telephone);
        userModel.setRegisterMode("byphone");
        userModel.setEncrptPassword(this.EncodeByMd5(password));
        userService.register(userModel);
        return CommonReturnType.create(null);

    }
    public String EncodeByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //确定计算方法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        //加密字符串
        String newstr = base64en.encode(md5.digest(str.getBytes("utf-8")));
        return newstr;
    }


    //用户获取opt短信接口
    @RequestMapping("/getotp")
    @ResponseBody
    public CommonReturnType getOpt(@RequestParam(value = "telephone")String phone){
        //按照一定规则生成OTP验证码
        Random random = new Random();
        int randomInt = random.nextInt(99999);
        randomInt+=10000;
        String optCode = String.valueOf(randomInt);

        //将OTP验证码同对应用户的手机号关联
        //暂时使用HttpSession方式绑定
        httpServletRequest.getSession().setAttribute(phone, optCode);
        System.out.println(httpServletRequest.getSession().getAttribute(phone));

        //将OTP验证码通过短信验证码发送给用户手机号和OPTCODE
        //此处省略, 我们直接打印,用户的敏感信息是不能打印到日志的, 企业中不能这样使用
        System.out.println("telephone : " + phone + " optCode = " + optCode);


        //TODO: 分布式项目中我们使用redis存储这一部分键值对

        return CommonReturnType.create(null);
    }



    @RequestMapping("/get")
    public CommonReturnType getUser(@RequestParam(value="id") Integer id) throws BusinessException {
        //调用service服务获取对应id的用户对象并返回给前端
        UserModel userModel = userService.getUserById(id);

        //若用户不存在
        if(userModel == null) {
            throw new BusinessException(EmBusinessError.USER_NOT_EXITS);
        }


        //将Model层转化为可供前端使用的UI模型
        UserVo userVo =  convertFromModel(userModel);
        //返回通用对象
        return CommonReturnType.create(userVo);
    }
    public UserVo convertFromModel(UserModel userModel){
        if(userModel == null) {
            return null;
        }
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(userModel, userVo);
        return userVo;
    }
    //定义exceptionhandler解决未被controller层吸收的exception
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody//注意还要添加这个注解用来返回数据
    public Object handlerException(HttpServletRequest request, Exception ex) {
        if(ex instanceof BusinessException) {
            BusinessException businessException = (BusinessException)ex;
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("errCode", businessException.getErrCode());
            responseData.put("errMsg", businessException.getErrMsg());
            return CommonReturnType.create(responseData,"fail");
        }else{
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("errCode", EmBusinessError.UNKNOWN_ERROR.getErrCode());
            responseData.put("errMsg", EmBusinessError.UNKNOWN_ERROR.getErrMsg() );
            return CommonReturnType.create(responseData,"fail");
        }


    }

    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }
}
