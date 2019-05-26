package com.spring.ym.executePrinciple;

import com.spring.ym.BeanLifeCycle_std.initializing_ds.Cat;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.*;
import org.springframework.stereotype.Component;

@Component
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {


    /**
     * BeanDefinitionRegistry Bean的定义信息的保存中心，
     *      以后BeanFactory就是按照BeanDefinitionRegistry里面保存的每一个bean的定义信息创建bean实例
     *          所以我们可以在该容器中注册bean
     *
     */
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        System.err.println("postProcessBeanDefinitionRegistry...." + beanDefinitionRegistry.getBeanDefinitionCount());
        //注册bean : 以下两种bean的创建方式都是一样的
        //RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(Cat.class);
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.rootBeanDefinition(Cat.class).getBeanDefinition();
        beanDefinitionRegistry.registerBeanDefinition("cat",beanDefinition);

    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        System.out.println("postProcessBeanFactory...." + configurableListableBeanFactory.getBeanDefinitionCount());
    }
}
