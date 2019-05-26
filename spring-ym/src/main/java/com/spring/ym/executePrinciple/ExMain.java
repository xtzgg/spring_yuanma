package com.spring.ym.executePrinciple;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Date;

public class ExMain {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ExtConfig.class);

        //发布一个事件
        applicationContext.publishEvent(new ApplicationEvent(new String("发布一个事件")) {
            @Override
            public Object getSource() {
                return super.getSource();
            }
        });
        applicationContext.close();

    }
}
