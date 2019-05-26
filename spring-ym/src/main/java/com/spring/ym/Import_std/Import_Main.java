package com.spring.ym.Import_std;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 给容器中注册组件
 *  1)包扫描 + 组件标注注解(@controller @Service @Repository @Component) [自己写的类]
 *  2)@Bean[导入的第三方包里面的组件]
 *  3) @Import[快速给容器中导入一个组件]
 *          1 @import(要导入的容器中的类) 容器中就会有该类信息，id默认为该类的全类名
 *          2 ImportSelector：返回需要导入组件的全类名数组
 *          3 ImportBeanDefinitionRegistrar ：所有的注册bean都在
 *                              BeanDefinitionRegistry 该类中注册:手工注册bean
 *
 */
public class Import_Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Import_config.class);
        String[] names = applicationContext.getBeanDefinitionNames();
        for (String name : names) {
            System.err.println(name);
        }
    }
}
