package com.spring.ym.Import_std;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class MyBeanDefinitionRegistry implements ImportBeanDefinitionRegistrar {
    /**
     *
     * @param annotationMetadata : 当前类的注解信息
     * @param beanDefinitionRegistry ：BeanDefinition注册类
     *                                 吧所有需要添加到容器中的bean添加到
     *                                    beanDefinitionRegistry中，手工注册
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        boolean read = beanDefinitionRegistry.containsBeanDefinition("com.spring.ym.Import_std.Read");
        boolean blue = beanDefinitionRegistry.containsBeanDefinition("com.spring.ym.Import_std.Blue");
        if(read && blue){
            // 指定bean定义信息，(Bean的类型 等等各种信息...)
            RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(RainBow.class);
            // 注册一个bean，指定bean名                      这里可以自定义bean名
            beanDefinitionRegistry.registerBeanDefinition("rainBow",rootBeanDefinition);
        }
    }
}
