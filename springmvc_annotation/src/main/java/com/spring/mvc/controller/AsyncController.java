package com.spring.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

@Controller
public class AsyncController {
    /**
     * 1、控制器返回callable
     * 2、springMVC异步处理，将Callable提交到TaskExecutor(springmvc提供)，使用一个隔离的线程进行执行
     * 3、DispatcherServlet和所有的Filter退出Web容器的线程，但是response保持打开状态；
     * 4、callable返回结果，springmvc将请求重新派发给容器，恢复之前的处理；
     * 5、根据callable返回的结果，springMVC继续进行视图渲染流程等等(从收请求-视图渲染)
     * =================/springMVC_annotation_war_exploded/async01=================
     * preHandle......
     * 主线程开始.....Thread[http-nio-3030-exec-7,5,main]===>1558964093254
     * 主线程结束.....Thread[http-nio-3030-exec-7,5,main]===>1558964093257
     * ===================DispatcherServlet及所有的Filter退出线程=========================================
     *
     * =================等待callable执行=====================================
     * 主线程.....Thread[MvcAsync1,5,main]===>1558964093280
     * 主线程.....Thread[MvcAsync1,5,main]===>1558964095281
     * =================callable执行完成=====================================
     *
     * ==============/springMVC_annotation_war_exploded/async01========================================
     * preHandle......
     * postHandle......(Callable的之前的返回值就是目标方法的返回值，副线程就不用再执行了)
     * afterCompletion......
     *
     * 异步拦截器：
     *      1)、原生API的AsyncListener
     *      2)、SpringMVC：实现AsyncHandlerIntercept：
     */
    @RequestMapping("/async01")
    @ResponseBody
    public Callable<String> async01(){
        System.err.println("主线程开始....."+ Thread.currentThread()+"===>"+System.currentTimeMillis());
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.err.println("主线程....."+ Thread.currentThread()+"===>"+System.currentTimeMillis());
                TimeUnit.SECONDS.sleep(2);
                System.err.println("主线程....."+ Thread.currentThread()+"===>"+System.currentTimeMillis());
                return "async01";
            }
        };
        System.err.println("主线程结束....."+ Thread.currentThread()+"===>"+System.currentTimeMillis());
        return callable;
    }


    /**
     * 以下两个请求返回的订单信息都是一样的，
     *      调用deferredResult.setResult(s);时，返回的DeferredResult会setResult的结果信息
     * @return
     */
    @RequestMapping("/createOrder")
    @ResponseBody
    public DeferredResult<Object>  createOrder(){
        //3秒超时之后会返回后边的信息
        DeferredResult<Object> result = new DeferredResult<>(3000L,"create fail....");
        DeferredResultQueue.save(result);
        return result;
    }
    @RequestMapping("/create")
    @ResponseBody
    public String  create(){
        //创建订单
        String s = UUID.randomUUID().toString();
        DeferredResult<Object> deferredResult = DeferredResultQueue.get();
        deferredResult.setResult(s);
        return "order....."+ s;
    }

}
