package com.spring.ym.value_properties;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

public class Value_Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ValueConfig.class);
        Animal animal = (Animal)applicationContext.getBean("animal");
        System.err.println(animal.toString());

        //也可以通过environment对象来获取 也可以通过@value注解来获取
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        String property = environment.getProperty("animal.height");
        System.err.println(property);
    }
}
