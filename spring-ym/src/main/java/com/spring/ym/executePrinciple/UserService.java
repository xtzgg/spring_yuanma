package com.spring.ym.executePrinciple;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @EventListener(classes = {ApplicationEvent.class})
    public void listen(ApplicationEvent applicationEvent){
        System.out.println("userService---监听到的事件: " + applicationEvent);
    }
}
