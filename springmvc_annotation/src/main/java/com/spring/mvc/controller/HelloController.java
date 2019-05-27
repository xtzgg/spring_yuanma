package com.spring.mvc.controller;

import com.spring.mvc.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    @Autowired
    HelloService helloService;
    @ResponseBody
    @RequestMapping("/hello")
    public String hello(){
        helloService.sayHello("tomcat...");
        return "success";
    }
    @RequestMapping("/success")
    public String success(){
        return "success";
    }
}
