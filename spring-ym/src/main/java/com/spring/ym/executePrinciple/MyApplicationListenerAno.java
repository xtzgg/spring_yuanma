package com.spring.ym.executePrinciple;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MyApplicationListenerAno{
    //当容器中发布此事件以后，方法触发
    @EventListener
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        System.out.println("收到事件EventListener：" + applicationEvent);
    }
}
