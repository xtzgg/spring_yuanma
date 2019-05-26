package com.spring.ym.factoryBean;

import com.spring.ym.Import_std.Color;
import org.springframework.beans.factory.FactoryBean;
//创建一个spring定义的FactoryBean
public class ColorFactory implements FactoryBean<Color> {
    //返回一个color对象，这个对象会添加到容器中
    @Override
    public Color getObject() throws Exception {
        return new Color();
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }
    //默认true 单实例，容器中只会保存一份
    //false，多实例，每次获取都会创建一个新的bean;
    @Override
    public boolean isSingleton() {
        return true;
    }
}
