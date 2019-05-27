package com.spring.ym.servlet3_0;

import javax.servlet.*;
import java.io.IOException;

public class UserFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //过滤请求
        System.err.println("userFilter...doFilter....");
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
