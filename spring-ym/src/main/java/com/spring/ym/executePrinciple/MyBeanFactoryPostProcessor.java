package com.spring.ym.executePrinciple;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        System.out.println("MyBeanFactoryPostProcessor....create");
        int count = configurableListableBeanFactory.getBeanDefinitionCount();
        System.out.println("当前beanfactory中有"+count +"个");
        String[] names = configurableListableBeanFactory.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }
    }
}
