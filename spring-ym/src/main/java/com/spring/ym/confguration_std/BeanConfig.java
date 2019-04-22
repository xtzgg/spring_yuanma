package com.spring.ym.confguration_std;

import com.spring.ym.entity.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//配置类 == 配置文件
@Configuration //告诉spring这是一个配置类
public class BeanConfig {
    //给容器注册一个Bean，类型为返回值的类型，id默认是用方法名作为id
    @Bean
    public Person person(){
        return new Person(18,"lili");
    }
    @Bean("person1")
    public Person person01(){
        return new Person(18,"lucy");
    }


}
