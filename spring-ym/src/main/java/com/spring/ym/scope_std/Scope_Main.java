package com.spring.ym.scope_std;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Scope_Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Scope_config.class);
        System.err.println("容器初始化完成......");
        Object person1 = applicationContext.getBean("person");
        Object person2 = applicationContext.getBean("person");
        System.err.println(person1 == person2); //比较两个对象是否是同一个对象
    }
}
