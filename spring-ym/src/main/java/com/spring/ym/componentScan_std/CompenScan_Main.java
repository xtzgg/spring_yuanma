package com.spring.ym.componentScan_std;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CompenScan_Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ComponeConfig.class);
        //获取容器中所有的bean的名字；配置扫描中的注解类会自动注入spring IOC容器中
        String[] definitionNames = applicationContext.getBeanDefinitionNames();
        for (String definitionName : definitionNames) {
            System.out.println(definitionName);
        }
    }
}
