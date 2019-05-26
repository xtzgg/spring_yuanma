package com.spring.ym.BeanLifeCycle_std.BeanPostProcessor;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class Amt implements ApplicationContextAware {
    private ApplicationContext applicationContext;
    /**
     * ApplicationContextAwareProcessor implements BeanPostProcessor
     *  调用postProcessBeforeInitializationf方法，判断是否是ApplicationContextAware 对象，如果是，则直接调用set方法将容器传递过来
     *              if (bean instanceof ApplicationContextAware) {
     *                 ((ApplicationContextAware)bean).setApplicationContext(this.applicationContext);
     *             }
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
