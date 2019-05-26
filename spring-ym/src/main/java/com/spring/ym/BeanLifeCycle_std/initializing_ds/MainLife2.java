package com.spring.ym.BeanLifeCycle_std.initializing_ds;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * bean的生命周期：
 *      2) 通过让bean实现initializingBean(定义初始化逻辑): 一般在容器中对应bean加载完成(构造完成)之后执行的初始化操作
 *                       DisposableBean(定义销毁逻辑)
 *
 *
 */
public class MainLife2 {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(InitialConfig.class);
        String[] names = applicationContext.getBeanDefinitionNames();
        for (String name : names) {
            System.err.println(name);
        }
        applicationContext.close();
    }
}
