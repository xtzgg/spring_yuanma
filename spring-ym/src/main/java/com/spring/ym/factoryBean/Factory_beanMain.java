package com.spring.ym.factoryBean;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 接@import
 * 给容器中注册组件
 * 4) 使用spring提供的factoryBean(工厂Bean)  …………
 *          该类在 spring与其他框架整合时用的非常多，所以需要重点掌握
 *          1）默认获取到的是工厂bean调用getObject创建的对象
 *          2）要获取工厂Bean本身，我们需要给id前面加一个& ：如: &colorFactory
 */
public class Factory_beanMain {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(FBConfig.class);
        Object bean = applicationContext.getBean("colorFactory");
        //bean的类型 可以看到工厂bean获取到的是调用getObject创建的对象
        System.err.println("bean的类型：" + bean.getClass());
        //想要得到colorFactory本身
        //可以查看BeanFactory spring在该类中定义bean &前缀，可以确定获取该bean工厂本身：String FACTORY_BEAN_PREFIX = "&";
        Object bean1 = applicationContext.getBean("&colorFactory");
        System.err.println(bean1.getClass());
        //或者使用
        ColorFactory bean2 = applicationContext.getBean(ColorFactory.class);
        System.err.println(bean2.getClass());

    }
}
