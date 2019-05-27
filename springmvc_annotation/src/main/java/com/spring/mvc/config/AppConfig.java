package com.spring.mvc.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

//springmvc只扫描controller；子容器
//useDefaultFilters=false 禁用默认的过滤规则
@ComponentScan(value = "com.spring.mvc",includeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION,classes = {Controller.class})}
        ,useDefaultFilters=false
)
@EnableWebMvc
public class AppConfig extends WebMvcConfigurerAdapter {
    //定制

    //视图解析器
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        //默认所有的页面都是从WEB-INF下的xxx.jsp页面
        //registry.jsp();// return this.jsp("/WEB-INF/", ".jsp");

        //也可以自定义
        registry.jsp("/WEB-INF/views/",".jsp");
    }
    //静态资源访问 不配置的时候，页面引入图片显示不出来
    //该请求被springmvc处理了，控制台打印no mapping found 呢个图片，
    //实际我们需要将静态资源交给tomcat来处理，就需要配置
    /**
     * 暂时没有测出来此作用？？？
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer defaultServletHandlerConfigurer) {
            defaultServletHandlerConfigurer.enable();//相当于在xml中配置：<mvc:default-servlet-handler/>静态资源管理
    }
    //定制拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
       registry.addInterceptor(new MyInterceptor())
               .addPathPatterns("/**");// /**: 表示不管多少层，任意请求都会被拦截
    }
    //以后还有很多都可以定制处理，可以参照spring mvc的官方文档.....
}
