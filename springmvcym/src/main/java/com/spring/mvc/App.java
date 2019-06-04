package com.spring.mvc;

/**
 * jsp: 翻译型语言
 * velocity：解释性
 * 性能：jsp > velocity
 * 开发体验上：velocity>jsp
 * jsp问题：嵌入java代码: 违反了mvc的三层架构的规则
 *
 *
 * servlet处理过程：
 *                                          //jsp路径(jsp本身也是servlet，对视图做一个渲染，返回)
 *  web请求----servlet--处理业务逻辑，设置model，属性：setAttribute----doRequestDispatcher(path)--forward转发业务逻辑--返回视图---response---静态页面结束
 *
 * springmvc的处理过程：
 * request请求---->dispatchServlet---->forward转发到control----业务逻辑处理，设置model(modelAndView),设置返回页面
 *          ----->返回处理结果给dispatcherServlet---->转发至视图(到view)---->基于模板与模型转换输出(view层)---> 基于response视图(返回到web端)
 *
 * springmvc上下文初始过程
     *  WebContextApplication的初始过程：
     *  handlerMappings的初始过程：
     *  handlerExceptionResolvers的初始过程：
     *  viewResolvers的初始过程：
     *  handlerAdapters的初始过程：
 *
 *
 * springmvc的请求过程总结：//disPatcherServlet整个处理过程
 *  1 调用doDispatch()
 *  2 遍历handlerMappings与request获取一个执行链getHandler()
 *  3 遍历handleAdapters与handle获取一个handle适配器
 *  4 通过执行链去调用拦截器当中的preHandle(),进行预处理
 *  5 预处理失败，整个失败，
 *      成功之后：
 *          基于handle适配器，去调用handle方法，返回modelAndView
 *  6 通过执行链去调用拦截器当中的postHandle()方法，进行拦截处理
 *  7 processDispatchResult()
 *      7.1 正常：调用render()进行视图解析
 *           7.1.1 基于遍历viewResolver与viewName获取view
 *           7.1.2 调用view的render方法进行视图解析和返回，设置model值request
 *                  ：如Jstl：预处理response；
 *                              遍历model里面的属性进行设置等，
 *                              include(包含页面输出)
 *                              forward(转发) //接下来跟servlet处理一样了
 *                                  把结果进行合并输出等....
 *      7.2 异常：遍历HandlerExceptionResolvers调用resolveException() 返回mv；跳转至异常mv；
 *
 *
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }
}
