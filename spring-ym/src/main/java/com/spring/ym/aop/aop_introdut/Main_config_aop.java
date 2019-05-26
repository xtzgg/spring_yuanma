package com.spring.ym.aop.aop_introdut;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * AOP: [动态代理]
 *      指定程序运行期间动态将某段代码切入到指定位置进行的编程方式
 *
 *  1 导入aop模块，Spring AOP；(spring-aspects)
 *  2 定义一个业务逻辑类(MathCaculator): 在业务逻辑运行的时候将日志进行打印(方法之前，方法运行之后，出现异常等)
    3 定义一个日志切面类(LogAspects);切面类里面的方法需要动态感知MathCaculator.div运行到哪里，然后执行；
            通知方法：
                前置通知(@Before)：logStart：在目标方法(div)运行之前运行
                后置通知(@After): logStart：在目标方法(div)运行结束之后运行 : (无论方法是正常结束还是异常结束)
                返回通知(@AfterReturning): logStart：在目标方法(div)运行正常返回之后运行
                异常通知(@AfterThrowing)： logStart：在目标方法(div)出现异常以后运行
                环绕通知(@Around)： 动态代理，手动推进目标方法运行(joinPoint.procced() );
    4 给切面类的目标方法标注合适何地运行(通知注解)
    5 将切面类和业务逻辑类(目标方法所在的类)都加入到容器中
    6 必须告诉spring容器哪个类是切面类(给切面类上加一个注解 @Aspect)
                            告诉spring容器哪个是切面类
    7 给配置类中加 @EnableAspectJAutoProxy [开启基于注解的aop模式]    重点
            在spring中有很多@EnableXXX，都是为了开启某项功能
    注意：JoinPoint joinPoint,必须写在参数的第一位，否则spring是无法识别的

 三步：
    1)、将业务逻辑组件和切面类都加入到容器中，告诉spring哪个是切面类
    2)、切面类上每一个通知方法都要标注通知注解，告诉spring何时运行
                主要写好切入点表达式(参照官方文档)
    3)、开启基于注解的aop模式
            @EnableAspectJAutoProxy
 AOP原理：[看给容器中注册了什么组件，这个组件什么时候工作，包括这个组件工作时候的功能是什么? ]
     从 @EnableAspectJAutoProxy 入手研究原理
    1、@EnableAspectJAutoProxy是什么?
           a @Import({AspectJAutoProxyRegistrar.class}),给容器中导入AspectJAutoProxyRegistrar组件
                利用AspectJAutoProxyRegistrar 给容器中注册自定义组件：
                    ：源码中该类实现了自定义注册bean的组件：
                            ：class AspectJAutoProxyRegistrar implements ImportBeanDefinitionRegistrar
                DUBUG，根据源码注册了哪些bean：
                        internalAutoProxyCreator = AnnotationAwareAspectJAutoProxyCreator(注：容器中注册名前者，前者的类型是后者)
                    如果没有，则会创建 一个 AnnotationAwareAspectJAutoProxyCreator
                        ：RootBeanDefinition beanDefinition = new RootBeanDefinition(cls);
                    该bean的名字就叫internalAutoProxyCreator，注册到了BeanDefinitionRegistry中
                        ：registry.registerBeanDefinition("org.springframework.aop.config.internalAutoProxyCreator", beanDefinition);
        总结：给容器中注册一个AnnotationAwareAspectJAutoProxyCreator
    2、研究AnnotationAwareAspectJAutoProxyCreator功能
            拓展：如果以后研究组件，就可以从enable入手进行研究，注入的组件研究透了，原理也就有了
            AnnotationAwareAspectJAutoProxyCreator
                -> 父类：AspectJAwareAdvisorAutoProxyCreator
                    -> 父类：AbstractAdvisorAutoProxyCreator
                            -> 父类：AbstractAutoProxyCreator
                                    -> 实现了接口：SmartInstantiationAwareBeanPostProcessor, BeanFactoryAware
                                //所以要关注后置处理器(在bean初始化前后完成事情) ，自动装配：BeanFactoryAware 获取一个beanFactory对象
    3、分析AnnotationAwareAspectJAutoProxyCreator作为后置处理器做了哪些工作
          从AbstractAutoProxyCreator 开始分析
             AbstractAutoProxyCreator.setBeanFactory()
             AbstractAutoProxyCreator.有后置处理器的逻辑，打断点
          再者AbstractAdvisorAutoProxyCreator
             重写了setBeanFactory方法
                        里面调用了.initBeanFactory()
          再看AnnotationAwareAspectJAutoProxyCreator
                .initBeanFactory() ；给init打上断点，给父类的setBeanFactory打上断点进行分析
    4 、断点分析：
          记录方法的流程：
                1) 传入主配置类，创建ioc容器
                2) 注册配置类，调用refresh()方法刷新容器
                3）registerBeanPostProcessors(beanFactory); 注册bean的后置处理器来方便拦截bena的创建
                    1)、先获取ioc容器已经定义了的需要创建对象的所有BeanPostProcessor
                    2)、给容器中加到BeanPostProcessor
                    3)、优先注册实现了PriorityOrdered接口的BeanPostProcessor；
                    4)、再给容器中注册实现类Ordered接口的BeanPostProcessor；
                    5)、再注册未实现优先级接口的BeanPostProcessor
                    6)、注册BeanPostProcessor，实际上就是创建BeanPostProcessor对象，保存到容器中
                         创建internalAutoProxyCreator的BeanPostProcessor[AnnotationAwareAspectJAutoProcessor]
                            1)、创建Bean的实例
                            2)、populateBean，给bean的各种属性赋值
                            3)、initializeBean;初始化Bean
                                    1）invokeAwareMethods(): 处理Aware解耦的方法回调
                                    2) applyBeanPostProcessorsBeforeInitialization 应用后置处理器的postProcessBeforeInitialization
                                    3) invokeInitMethods();执行自定义的初始化方法
                                    4) applyBeanPostProcessorsAfterInitialization，执行后置处理器的postProcessAfterInitialization
                            4) BeanPostProcessor(AnnotationAwareAspectJAutoProcessor创建成功-->aspectJAdvisorBuilder)
                        7）吧BeanPostProcessor注册到BeanFactory中：
                                beanFactory.addBeanPostProcessor(postProcessor)
//-=-------------------以上是创建和注册AnnotationAwareAspectJAutoProxyCreator的过程==========
            AnnotationAwareAspectJAutoProxyCreator ---> 类型 ：InstantiationAwareBeanPostProcessor
     5、继注册bean之后，我们进行了完成最后的bean的初始化加载
            this.finishBeanFactoryInitialization(beanFactory); 完成beanFactory初始化工作，创建剩下的单实例bean
                1)、遍历获取容器中所有的Bean，依次创建对象getBean(beanName);
                            getBean--> doGetBean -> getSingleton()
                2)、创建bean
                    【AnnotationAwareAspectJAutoProxyCreator会在任何bean创建之前会有一个拦截：InstantiationAwareBeanPostProcessor 会调用里面的方法】
                    1) 先从缓存中获取当前bean，如果能获取到，说明bean是被创建过的
                            直接使用，否则再创建(保证单实例创建一次)
                            质押创建好的额Bean都会被缓存起来
                    2) createBean：创建bean：综下研究：AnnotationAwareAspectJAutoProxyCreator会在任何bean创建之前先尝试返回bean实例

                            【BeanPostProcessor是在Bean对象创建完成初始化前后调用的】
                            【是在创建bean实例之前先尝试用后置处理器返回对象的】
                                    ------------所以两个beanPostProcessor功能是不一样的

                           1)、 this.resolveBeforeInstantiation(beanName, mbdToUse);
                                    希望后置处理器在此能返回一个代理对象；
                                    如果能返回代理对象，则返回使用；如果不能返回，则继续
                                        调用的方法是：
                                            applyBeanPostProcessorsBeforeInstantiation
                                            applyBeanPostProcessorsAfterInitialization
                                        该方法是实现了接口InstantiationAwareBeanPostProcessor里面的方法
                            2)、doCreateBean(beanName,mbdToUse,args);真正的去创建一个bean实例和4.6的bean创建流程一样
                                     创建bean实例：
                                     this.createBeanInstance(beanName, mbd, args);
                                     属性赋值
                                     this.populateBean(beanName, mbd, instanceWrapper);
                                     初始化bean
                                      this.initializeBean(beanName, exposedObject, mbd);
                                            ：该方法里面执行过程：
                                                后置处理器的before
                                                this.applyBeanPostProcessorsBeforeInitialization
                                                初始化方法
                                                this.invokeInitMethods(beanName, wrappedBean, mbd);
                                                //后置处理器的after
                                                this.applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
                            3) 后置处理器先尝试返回对象

// initializeBean流程：
        1 invokeAwareMethods
        2 before后置处理器
        3 invokeInitMethods
        4 after后置处理器

AspectJAwareAdvisorAutoProxyCreator【InstantiationAwareBeanPostProcessor】 的作用：
    1)、每一个bean创建之前，调用 postProcessBeforeInstantiation()方法
            关心MathCaculator和LogAspect的创建之前，这个smarter后置处理器做了哪些工作
            1)、判断当前bean是否在advisedBeans中(保存了所欲需要增强的bean)
            2)、判断当前bean是否是基础类型(isInfrastructureClass)的(实现了以下接口：Advice,Pointcut,Advisor,AopInfrastructureBean)
重点强调                 【说明：并且该类重写了该方法，所以一定要看该方式的impl。而不要直接点进去看父类的方法，】
                AspectJAwareAdvisorAutoProxyCreator里面有方法isInfrastructureClass
                        进行了@Aspect判断的isAspect注解的判断
            3)、是否需要跳过shouldSkip()
                1)、List<Advisor> candidateAdvisors = this.findCandidateAdvisors();
                    获取候选增强器(增强器就是我们@Aspect类中的@After@Before@PointCut等等通知方法，每个通知方法就是一个增强器)
                2)、每个增强器的类型为：InstantiationModelAwarePointcutAdvisor
                        判断每个增强器是否是AspectJPointcutAdvisor类型，显然不是的
                3)、永远返回false，调用Main_config_aop 方法创建MathCaculator对象
                4)、创建完成之后，回头调用 postProcessAfterInitialization
                        方法里面 ：return this.wrapIfNecessary(bean, beanName, cacheKey); //包装如果需要的情况下
                                1）: this.getAdvicesAndAdvisorsForBean ： Object[] specificInterceptors
                                        1):获取当前bean的候选的所有增强器(通知方法)   //候选增强器
                                            找到能在当前bean使用的增强器(哪些方法是需要切入当前bean的方法的)
                                                ：canApply(candidate, clazz, hasIntroductions) ：判断增强器是否能用(能匹配pintCut()表达式)
                                                能用，则都加入到了 List<Advisor> eligibleAdvisors.add(candidate);
                                        2)：获取到能在bean使用的增强器
                                        3): 给增强器排序
                                2)：保存当前bean在advisedBeans中：
                                3): 如果当前bean需要增强，创建当前bean的代理对象：Object proxy = this.createProxy
                                        1): 获取所有增强器(通知方法)： Advisor[] advisors = this.buildAdvisors(beanName, specificInterceptors);
                                        2): 保存到proxyFactory    // proxyFactory.addAdvisors(advisors);
                                        3): 创建代理对象：spring自动决定    //（让代理工厂创建代理对象：proxyFactory.getProxy(this.getProxyClassLoader());）
                                                 new ObjenesisCglibAopProxy(config)//cglib动态代理
                                                 new JdkDynamicAopProxy(config)  //jdk的动态代理 (如果有实现jdk接口的我们用这个来创建动态代理)
                                4)、给容器中返回当前组件使用cglib增强了的代理对象：// return proxy;
                                5)、以后容器中获取到的就是这个组件的代理对象，执行目标方法的时候，代理对象就会执行通知方法的流程

========================33AOP获取拦截器链==================
    2）、目标方法的执行
        【断点打到目标方法：除法 caculator.div(1, 2);开始研究整个执行流程，点进去】
                【在该断点，我们看到caculator的确是cglib增强后的对象】
        容器中保存了组件的代理对象(cglib增强后的对象)，这个对象里面保存了详细信息(比如增强器，目标对象，xxxxxx)
        1）、cglibProxy.intercept(); //拦截目标方法的执行
        2)、根据proxyFactory(this.advisor)获取需要执行的目标方法拦截器链
                List<Object> chain = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, targetClass);
                【研究如何获取拦截器链】
                1)、 List<Object> interceptorList 保存了所有拦截器  //个数：5
                       一个默认的ExposeInvocationInterceptor 和四个增强器;
                2)、遍历所有的增强器：将其转为interceptor，  //调用：registry.getInterceptors(advisor);
                3)、将增强器转为我们要用的  List<MethodInterceptor> interceptors
                        1)、如果是MethodInterceptor，直接加入集合中
                        2)、如果不是，使用AdvisorAdapter将增强器转为MethodInterceptor
                        3)、转换完成，返回List<MethodInterceptor>集合
        3)、如果没有拦截器链，直接执行目标方法
                    拦截器链（每个通知方法又被包装为方法拦截器，方法的执行都是利用MethodInterceptor机制）
        4)、如果有拦截器链，把需要执行的目标对象，目标方法，
                拦截器链等信息传入创建一个CglibMethodInvocation,
                并调用  Object retVal = mi.proceed();
        5)、拦截器链的触发过程
                創建cglibMethodInvocation對象
                    记录当前拦截器的索引
                1) 如果没有拦截器，直接执行目标方法，或者拦截器的索引和拦截器数组-1大小一样(执行到最后一个拦截器)
                [执行目标方法]
                2) invoke(this) //this就是cglibMethodInvocation对象
                    invocation就是一个threadLocal，同一个线程共享数据
                    mi就是cglib的invocation，将methodInvocation对象放入到
                    invocation中去，执行mi.proceed() 方法；
                    进去之后同样的流程，只是索引从当前的0变为1
                    这次从里面拿到第二个拦截器
                    ...
                    也就是每次执行proceed方法，索引都会自增，然后将拦截器放入invocation中去
                    链式的方式锁定到下一个拦截器
                    beforeadivceIntercept---->调用前置通知 ---->调用目标方法
                    ---->mi.proceed()---->invokeJoinpoint()
                    ----->返回到advisorAfter--->finally（执行后置通知）
                    ----->抛异常抛给上层，然后执行--->afterReturning--->执行异常通知
                    ----->mi.proceed执行没有异常，则执行afterReturning---->执行返回通知
                    有异常执行异常通知，没有异常执行返回通知
                2) 链式获取每一个拦截器，拦截器执行invoke方法，每个拦截器等待下一个拦截器完成返回以后再来执行
                拦截器链的机制，保证通知方法和目标方法的执行顺序
//35 AOP原理总结============================================
 1 @EnableAspectJAutoProxy 开启AOP的功能
 2 @EnableAspectJAutoProxy 给容器中注册一个组件：AspectJAwareAdvisorAutoProxyCreator
 3 而这个AspectJAwareAdvisorAutoProxyCreator组件是个后置处理器
 4 容器的创建流程
        1) refresh--->注册后置处理器方法-->创建AspectJAwareAdvisorAutoProxyCreator对象
        2) finishBeanFactoryInitialization()初始化剩下的单实例bean
                1) 创建业务组件和切面组件
                2) AspectJAwareAdvisorAutoProxyCreator拦截组件的创建过程
                3) 组件创建之后，判断组件是否需要增强
                        是：切面的通知方法。包装成增强器(Advisor);给业务逻辑组件创建一个代理对象(cglib)
 5 执行目标方法
        1)、代理对象执行目标方法
        2)、cglibProxy.intercept();
            1)、得到目标方法的拦截器链(增强器包装成拦截器MethodInterceptor)
            2)、利用拦截器的链式机制，依次进入每一个拦截器进行执行
            3)、效果：
                   正常执行： 前置通知--->目标方法--->后置通知--->返回通知
                   异常执行： 前置通知--->目标方法--->后置通知--->异常通知
 */
@EnableAspectJAutoProxy
@Configuration
public class Main_config_aop {

   //业务逻辑类加入到容器中
    @Bean
    public MathCaculator caculator(){
        return new MathCaculator();
    }

    @Bean
    public LogAspect logAspect(){
        return new LogAspect();
    }
}
