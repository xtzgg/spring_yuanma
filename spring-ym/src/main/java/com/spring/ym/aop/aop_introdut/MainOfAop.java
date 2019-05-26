package com.spring.ym.aop.aop_introdut;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainOfAop {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Main_config_aop.class);
        MathCaculator caculator = applicationContext.getBean(MathCaculator.class);
       // int div =
                caculator.div(1, 2); //该对象只能从容器中获取，不能自己创建，不然那不起作用
       // System.err.println("该方法被调用了:"+div);
        applicationContext.close();

    }
}
