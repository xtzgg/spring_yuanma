package com.spring.ym.autowired_qualifier.profilec;

import com.spring.ym.autowired_qualifier.aware_s.Red;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;

/**
 * 1 使用命令行参数进行激活指定环境
 *          -Dspring.profiles.active=dev,test,prod
 * 2 可以用代码的方式来激活指定环境
 *      可以通过spring提供的environment来进行定义
 */
public class Profile_Main {
    public static void main(String[] args) {
       // AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Profile_config.class);

        //1 创建一个applicationContext
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        //2 设置需要激活的环境
        applicationContext.getEnvironment().setActiveProfiles("test");
        //3 注册主配置类
        applicationContext.register(Profile_config.class);
        //4 启动刷新容器
        applicationContext.refresh();

        String[] names = applicationContext.getBeanNamesForType(DataSource.class);
        for (String name : names) {
            System.err.println(name);
        }
        Red red = applicationContext.getBean(Red.class);
        System.err.println(red.getClass());
    }
}
