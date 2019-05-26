package com.spring.ym.transation;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main_TX {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(TxConfig.class);
        UserService userService = applicationContext.getBean(UserService.class);
        //userService.insertUser();
    }
}
