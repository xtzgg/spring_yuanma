package com.spring.ym.value_properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
//注解导入properties文件  读取外部配置文件中k/v保存到运行的环境变量中
//加载完外部的配置文件后通过${}变量来获取外部配置文件的值

//2 也可以通过environment对象获取属性值
@PropertySource(value={"classpath:animal.properties"},encoding = "utf-8")
public class ValueConfig {
    @Bean
    public Animal animal(){
        return new Animal();
    }
}
