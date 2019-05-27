#### shared libraries(共享库) / runtimes pluggability(运行时插件)
```
1、Servlet容器启动会扫描，当前应用里面的每一个jar包的ServletContainerInitializer的实现
2、提供ServletContainerInitializer的实现类：
    必须绑定在：META-INF/services/javax.servlet.ServletContainerInitializer
    文件内容就是javax.servlet.ServletContainerInitializer实现类的全类名
总结：容器在启动应用的时候，还扫描应用每个jar包里面
META-INF/services/javax.servlet.ServletContainerInitializer
指定的实现类，启动并运行这个实现类的方法,传入感兴趣的类型

ServletContainerInitializer
@HandlesTypes: 

3 使用ServletContext注册Web组件(Servlet. Filter . Listener)
    - 参看：MyServletContainerInitializer
      1) 使用ServletContext注册Web组件(Servlet. Filter . Listener)
      2) 使用编码的方式，在项目启动的时候给ServletContext添加组件
               必须再项目启动的时候来添加 【出于安全考虑】
               1) ServletContainerInitializer得到的ServletContext
               2) ServletContextListener的contextInitialized方法得到的ServletContext组件
                   ：注册web三大组件
      3) 容器启动后执行listener初始化操作，关闭tomcat容器，执行销毁操作；
4 springmvc整合servlet3.0 请看该git库对应的springmvc_annotation的这个项目README.md




```

