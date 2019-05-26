package com.spring.ym.BeanLifeCycle_std.jsr250;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 3) 可以使用JSR250：
 *  @PostConstruct: bean创建完成并且属性赋值完成，执行初始化方法
 *  @PreDestory ：在容器销毁bean之前通知我们进行清理工作
 */
public class LifeMain3 {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Gril_config.class);
        Object gril = applicationContext.getBean("girl");
        System.err.println(gril.getClass());
        applicationContext.close();
    }
}
