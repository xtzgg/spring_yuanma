package com.spring.ym.BeanLifeCycle_std.initializing_ds;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class Cat implements InitializingBean, DisposableBean {
    public Cat(){
        System.err.println("cat-----constructor-----");
    }

    @Override
    public void destroy() throws Exception {
        System.err.println("cat-----disposableBean-----");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.err.println("cat-----initializingBean---afterPropertiesSet");
    }
}
