package com.spring.ym.BeanLifeCycle_std.initAnddestory;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 初始化：对象创建完成，并赋值好，调用初始化方法
 * 销毁：单实例：容器关闭的时候
 *       多实例：容器不会管理这个bean，容器不会调用销毁方法，只能手动调用
 *  1）指定初始化和销毁方法
 *          通过@Bean指定init-method 和 destory-methods
 */
public class MainLife {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfLifeCycle.class);
        Car bean = applicationContext.getBean(Car.class);
        System.err.println(bean.getClass());
        applicationContext.close();
    }
}
