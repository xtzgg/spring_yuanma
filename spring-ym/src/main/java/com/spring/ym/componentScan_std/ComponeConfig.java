package com.spring.ym.componentScan_std;

import com.spring.ym.entity.Person;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

//配置类 == 配置文件
@Configuration //告诉spring这是一个配置类
//注解方式指定包扫描的路径
//@ComponentScan(value = "com.spring.ym.componentScan_std",
//                //excludeFilters
//                  includeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION,classes = {Controller.class, Service.class})}
//                  ,//注解中如何禁用默认规则(只有禁用了，includeFilters才能生效)
//                    useDefaultFilters = false
//                )
                    //filter的type有几种：注解，j表达式，类型，正则排除等,指定排除的注解类
//@ComponentScan value:指定要扫描的包
//excludeFilters = Filter[] 指定扫描的时候按照什么规则排除哪些组件
//includeFilters = Filter[] 指定扫描的时候只包含哪些组件

//这里也可以使用componentScans来指定多个componentScan规则，如果是jdk8以上，则可以直接写多个重复的注解：如下和上边效果是一样的
//@ComponentScans(
//        @ComponentScan(value = "com.spring.ym.componentScan_std",
//        //excludeFilters
//        includeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION,classes = {Controller.class, Service.class})}
//        ,useDefaultFilters = false
//))

//04节课讲的是Fiter过滤的几种规则：如下
/**
 * FilterType.ANNOTATION  根据注解进行排除
 * FilterType.ASSIGNABLE_TYPE 根据给定的类型
 * FilterType.REGEX    使用正则表达式指定规则
 * FilterType.ASPECTJ  使用ASPECTJ表达式过滤(不常用，不解释)
 * FilterType.CUSTOM  使用自定义规则，看源码解释：
 * {@link org.springframework.core.type.filter.TypeFilter } implementation //说明必须是该实现类
 *
 */
@ComponentScan(value = "com.spring.ym.componentScan_std",
        //excludeFilters
        includeFilters = {
//        @ComponentScan.Filter(type = FilterType.ANNOTATION,classes = {Controller.class}),
//        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,classes = CService.class),
        @ComponentScan.Filter(type = FilterType.CUSTOM,classes = MyTypeFilter.class)
        //注解中如何禁用默认规则(只有禁用了，includeFilters才能生效)
        },
        useDefaultFilters = false
)
public class ComponeConfig {
    @Bean
    public Person person(){
        return new Person(18,"lili");
    }

}
