package com.spring.ym.confguration_std;

import com.spring.ym.entity.Person;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 主要用于测试@configration注解和@Bean注解测试
 */
public class BeanMain {
    public static void main(String[] args) {
        //获取容器中的bean对象：使用配置文件中初始化的对象
 /*       ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/beans.xml");
        //Object person = applicationContext.getBean("person");
        Person person = applicationContext.getBean(Person.class);
        System.err.println(person.toString());//Person(age=18, name=lucy)*/

        //注解式开发
        AnnotationConfigApplicationContext configApplicationContext = new AnnotationConfigApplicationContext(BeanConfig.class);
//        Person person = configApplicationContext.getBean(Person.class); //这个需要改类型对象是单例模式
//        System.err.println(person);
        String[] beanNamesForType = configApplicationContext.getBeanNamesForType(Person.class);
        for (String s : beanNamesForType) {
            System.err.println(s); //person person1
        }
    }
}
