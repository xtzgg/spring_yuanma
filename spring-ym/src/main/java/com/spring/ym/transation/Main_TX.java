package com.spring.ym.transation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Locale;

public class Main_TX {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(TxConfig.class);
        UserService userService = applicationContext.getBean(UserService.class);
        //userService.insertUser();
        String message = applicationContext.getMessage("index.greeting", null, Locale.CHINA);
        System.err.println(message);
    }
}
