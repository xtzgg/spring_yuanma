package com.spring.ym.conditional_std;

import com.spring.ym.entity.Person;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Map;

/**
 * 该conditional注解是spring大量使用的，功能强大的注解
 */
public class Cond_Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Cond_config.class);
        //查看容器中有哪些人
        String[] namesForType = applicationContext.getBeanNamesForType(Person.class);
        for (String s : namesForType) {
            System.err.println(s);
        }
        Map<String, Person> beansOfType = applicationContext.getBeansOfType(Person.class);
        System.err.println(beansOfType);

        //=================spring可以通过该对象获取本机的环境及环境变量等信息==========
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        String property = environment.getProperty("os.name");
        System.err.println(property);//Windows 10


    }
}
