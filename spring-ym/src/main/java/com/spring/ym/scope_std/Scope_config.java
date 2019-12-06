package com.spring.ym.scope_std;

import com.spring.ym.entity.Person;
import org.springframework.context.annotation.*;

@Configuration
public class Scope_config {
    /**
     * prototype 多实例  ioc容器启动不会调用方法创建对象，从容器中获取几次就会创建几次
     *
     * singleton 单实例  ioc容器启动会调用方法创建对象放到ioc容器中，
     * 以后用到时直接从容器中拿(map.get())
     *
     * //这两种不用，一般需要时，将对象放入到对应requet和session中即可
     * session 同一个session创建一个实例
     * request 同一个请求创建一个实例
     *
     * 懒加载：
     *          单实例bean：默认在容器启动的时候创建对象；
     *          懒加载：容器启动不创建对象，第一次使用bean创建对象，并初始化；
     *
     *
     * @return
     */
    //@Scope(scopeName = "prototype")
    @Scope(scopeName = "singleton")
    @Lazy
    @Bean
    public Person person(){
        System.err.println("查看什么时候调用该方法创建对象......");
        return new Person(15,"jack");
    }
}
