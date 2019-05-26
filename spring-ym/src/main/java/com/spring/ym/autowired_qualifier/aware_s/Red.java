package com.spring.ym.autowired_qualifier.aware_s;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;
@Component
public class Red implements ApplicationContextAware, BeanNameAware, EmbeddedValueResolverAware {

    private ApplicationContext applicationContext;

    @Override
    public void setBeanName(String beanName) {
        //获取到当前bean的名字
        System.err.println(beanName); // red
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;//容器和底层的容器对象是同一个
    }

    /**
     * StringValueResolver: 解析springel表达式，或者${}等变量的值的解析器
     *
     */
    @Override
    public void setEmbeddedValueResolver(StringValueResolver stringValueResolver) {
        String s = stringValueResolver.resolveStringValue("你好：${os.name},我是#{20*18}!");
        System.err.println("解析的字符串是：" + s); //解析的字符串是：你好：Windows 10,我是360!
    }
}
