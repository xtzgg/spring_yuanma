package com.spring.ym.servlet3_0;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class UserListener implements ServletContextListener {
    //监听ServletContext销毁
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        System.err.println("userListener....contextInitialized");
    }
    //监听ServletContext启动初始化
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.err.println("userListener....contextDestroyed");
    }
}
