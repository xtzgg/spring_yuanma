package com.spring.mvc.service;

import org.springframework.stereotype.Service;

@Service
public class HelloService {
    public String sayHello(String str){
        System.err.println(str);
        return  str;
    }
}
