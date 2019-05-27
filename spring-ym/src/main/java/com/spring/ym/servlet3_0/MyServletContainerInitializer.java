package com.spring.ym.servlet3_0;

import javax.servlet.*;
import javax.servlet.annotation.HandlesTypes;
import java.util.EnumSet;
import java.util.Set;

/**
 * 容器启动的时候会将@HandlesTypes指定的这个类型下面的子类(实现类，子接口等)传递过来
 */
@HandlesTypes(value = {HelloServices.class})//传入感兴趣的类型
public class MyServletContainerInitializer implements ServletContainerInitializer {
    /**
     * 应用启动的时候，会运行onStartup方法；
     * servletContext：代表当前web应用的ServletContext；一个web应用一个ServletContext
     * ServletContext(四大域之一)
     *
     * Set<Class<?>> set:感兴趣的类型的所有子类型；
     *
     * 1) 使用ServletContext注册Web组件(Servlet. Filter . Listener)
     * 2) 使用编码的方式，在项目启动的时候给ServletContext添加组件
     *          必须再项目启动的时候来添加 【出于安全考虑】
     *          1) ServletContainerInitializer得到的ServletContext
     *          2) ServletContextListener的contextInitialized方法得到的ServletContext组件
     *              ：注册web三大组件
     *
     *
     */
    @Override
    public void onStartup(Set<Class<?>> set, ServletContext servletContext) throws ServletException {
        System.err.println("感兴趣的类型：" + set.toString());
        //注册组件
        ServletRegistration.Dynamic userServlet = servletContext.addServlet("userServlet", new UserServlet());
        //配置servlet的映射信息
        userServlet.addMapping("/user");
        //注册Listener
        servletContext.addListener(UserListener.class);
        //注册Filter
        FilterRegistration.Dynamic userFilter = servletContext.addFilter("userFilter", UserFilter.class);
        //拦截的类型                                                            isMatchType
        userFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST),true,"/*");

    }
}
