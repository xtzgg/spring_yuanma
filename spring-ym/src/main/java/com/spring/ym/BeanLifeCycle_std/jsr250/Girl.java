package com.spring.ym.BeanLifeCycle_std.jsr250;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
@Component
public class Girl {
    public Girl(){
        System.err.println("gril ----- constructor -- -");
    }
    //对象创建并赋值之后调用
    @PostConstruct
    public void init(){
        System.err.println("girl------postConstructor");
    }
    //容器销毁该对象之前调用该方法
    @PreDestroy
    public void destory(){
        System.err.println("girl ----- preDesotry-----");
    }
}
