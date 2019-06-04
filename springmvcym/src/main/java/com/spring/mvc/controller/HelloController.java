package com.spring.mvc.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 1 找到controller
 *      HandMapping:
 *          getHandler():
 * 2 配置适配器
 *      HandAdapter
     *      方法来判断controller是否可用：
     *           public boolean supports(Object handler) {
     *                  return handler instanceof Controller;
     *           }
   3 找到视图
        配置view
 */
public class HelloController {

    @RequestMapping("/user")
    public ModelAndView getName(HttpServletRequest request, HttpServletResponse httpServletResponse){
        ModelAndView modelAndView = new ModelAndView("/hello.jsp");
        modelAndView.addObject("name","xiaoming");
        return modelAndView;
    }
}
