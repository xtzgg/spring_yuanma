package com.spring.ym.BeanLifeCycle_std.initializing_ds;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = "com.spring.ym.BeanLifeCycle_std.initializing_ds")
public class InitialConfig {
//    @Bean
//    public Cat cat(){
//        return new Cat();
//    }
    @Bean
    public Dog dog(){
        return new Dog();
    }
}
