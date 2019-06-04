package com.spring.ym.conditional_std;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class LinuxCondition implements Condition {
    /**
     *
     * @param conditionContext  判断条件能使用的上下文（环境）
     * @param annotatedTypeMetadata 注释信息
     * @return
     */
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        //TODO 是否是linux系统
        //1 能获取到ioc使用的beanfactory
        ConfigurableListableBeanFactory beanFactory = conditionContext.getBeanFactory();
        //2 获取类加载器
        ClassLoader classLoader = conditionContext.getClassLoader();
        //3 获取当前环境信息(运行是的信息：包括环境变量，虚拟机的信息等)
        Environment environment = conditionContext.getEnvironment();
        //4 获取到bean定义的注册类(可以用于获取，添加，移除bean对象)(所有的bean都是在这里注册的)
        BeanDefinitionRegistry registry = conditionContext.getRegistry();

        //可以判断容器中的bean注册情况，也可以给容器中注册bean
        boolean definition = registry.containsBeanDefinition("person");

        String property = environment.getProperty("os.name");
        if(property.contains("Linux")){
            return true;
        }
        return false;
    }
}
