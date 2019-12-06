package com.spring.ym.IOC_total;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

/**
 * IOC总结：
     *     componentScan
 *      * @Bean
 *      * @Conditional  重点    spring底层用的非常多
 *      * @Import  重点
 *      *      importSelector
 *      *      importRegister
 *      * @Scope
 *      * @Value
 *      * @Autowired
 *      * @PropertySource
 *      * @Profile
 *      * 组件注入：applicationContextAware ----Processor
 *
 *      后续开始学习：AOP和声明式事务
 *
 *      循环依赖演示：
 */
public class IOC_Main {
    public static void main(String[] args) {
        final AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ExtConfig.class);
    }
}
