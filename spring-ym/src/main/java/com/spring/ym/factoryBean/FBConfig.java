package com.spring.ym.factoryBean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FBConfig {
    @Bean
    public ColorFactory colorFactory(){
        return new ColorFactory();
    }
}
