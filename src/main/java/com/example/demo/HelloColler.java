package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Green写代码
 * @date 2023-04-09 14:51
 */
@RestController
public class HelloColler {
    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }
}
