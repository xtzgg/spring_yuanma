package com.spring.ym.BeanLifeCycle_std.initAnddestory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * bean的生命周期
 *      bean创建 --- 初始化 --- 销毁的过程
 * 容器管理bean的生命周期
 * 自定义初始化和销毁方法，容器在bean进行到当前的生命周期的时候来调用我们自定义的初始化和销毁方法
 *  1) 指定初始化和销毁方法
 *          a 以前的方式在xml里面配置init-method="无参可抛异常的方法" destory-method="无参可抛异常的方法"
 *          b 注解的方式来实现：
 *  2）
 */
@Configuration
public class MainConfigOfLifeCycle {
   // @Scope(scopeName = "prototype") 多实例销毁无法被容器管理
    @Bean(initMethod = "init",destroyMethod = "destory")
    public Car car(){
        return new Car();
    }
}
