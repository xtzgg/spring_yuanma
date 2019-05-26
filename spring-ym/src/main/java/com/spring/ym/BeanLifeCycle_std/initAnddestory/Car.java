package com.spring.ym.BeanLifeCycle_std.initAnddestory;

/**
 * 原来创建bean的情况：
 *      1 单实例：则在容器初始化的时候就会调用构造方法创建bean
 *      2 多实例：则每次获取该bean的时候，才会在容器中创建bean
 *
 */
public class Car {

    public Car(){
        System.err.println("car-----construtor-----");
    }
    public void init(){
        System.err.println("car----init-----");
    }
    public void destory(){
        System.err.println("car----destory-----");
    }
}
