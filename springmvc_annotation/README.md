
1 web容器在启动的时候，会扫描每个jar包下的META-INF/services/javax.servlet.ServletContainerInitializer
2 mvc的jar包会 ：加载这个文件指定的类：SpringServletContainerInitializer【看jar包】
3 spring的应用一启动会加载感兴趣的WebApplicationInitializer接口下的所有组件；
4 并且为WebApplicationInitializer组件创建对象(组件不是接口和抽象类)
    - AbstractContextLoaderInitializer：创建根容器
        - 添加该监听器到ServletContext中
    - AbstractDispatcherServletInitializer
        - 创建一个web的ioc容器，createServletApplicationContext();
        - 创建了new DispatcherServlet(servletAppContext);调用createDispatcherServlet()创建；
        - 将创建的DispatcherServlet添加到ServletContext中：
            - servletContext.addServlet(servletName, dispatcherServlet);
            - this.getServletMappings()
        - AbstractAnnotationConfigDispatcherServletInitializer：注解方式配置的DispatcherServlet初始化器 
            - 创建根容器： createRootApplicationContext() 相当于重写了父类的方法
            - 传入一个配置类：getRootConfigClasses
            - 创建web的ioc容器【重写了父类的方法】：createServletApplicationContext();
                获取配置类：servletAppContext.register(configClasses);
     总结：
        以注解方式来启动springmvc：继承 AbstractAnnotationConfigDispatcherServletInitializer
        实现抽象方法指定DispatcherServlet的配置信息； 【该类会自动将注解对象放入servletContext中】
 
 //======================================================================================       
 定制springMVC
 1)、@EnableWebMvc 开启springMVC定制配置功能
        <mvc:annotation-driven/>；
 2)、配置组件(视图解析器，视图映射，静态资源映射，拦截器....)
        实现接口：WebMvcConfigurer；我们一般用不到呢么多方法，所以我们继承WebMvcConfigurerAdapter类定制对应功能
---
 request----主线程池----response
 1) 之前tomcat是同步，所以主线程池耗完，则请求无法处理
 2) @WebServlet(value="/async",asyncSupported=true)
 //主线程开始
 AsyncContext startAsync = request.startAsync();
 startAsync.start(new Runnable(){
    //副线程开始
    //逻辑....
    startAsync.complete();
    //获取到异步上下文
    AsyncContext aStartAsync = request.getAsyncContext();
    //获取响应
    ServletResponse response = aStartAsync.getResponse();
    response.getWriter().write("hello async.....");
    //副线程结束
 })
 //主线程结束       
 注：tomcat主线程立即返回，逻辑业务交给副线程池
        -> springMvc已经维护了该业务线程池       
   
   springmvc也是基于servlet3进行异步封装   
     
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
        
   springMVC异步请求实际开发应用场景：
         请求开始----应用1(线程1不能完成创建订单)----发送消息给消息中间件-----应用2监听该消息中间件，监听到消息则完成创建订单工作
         应用2处理完返回结果-----给消息中间件----应用1(线程2监听拿到订单结果)---响应结果
   模拟以上简单的场景：
         调用deferredResult.setResult(s);时，返回的DeferredResult会setResult的结果信息 
         具体参看AsyncController中的两个请求
   
        
        
        
        
        