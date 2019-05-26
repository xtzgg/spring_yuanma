### spring容器创建过程回顾
##### 1 spring容器的refresh()【创建刷新】
```
1、prepareRefresh()刷新前的预处理；
    - this.initPropertySources();初始化一些属性设置；子类自定义个性化的属性设置方法
    - this.getEnvironment().validateRequiredProperties();校验属性的合法性
    - this.earlyApplicationListeners = new LinkedHashSet(this.applicationListeners);保存容器中一些早期的事件；
2、this.obtainFreshBeanFactory()；获取BeanFactory
    - this.refreshBeanFactory();刷新【创建】beanFactory，
        - 设置了序列化的id；创建一个 this.beanFactory = new DefaultListableBeanFactory();
    - getBeanFactory();返回刚才GenericApplicationContext创建的BeanFactory
    - 将创建的beanFactory【类型：DefaultListableBeanFactory】返回
3、this.prepareBeanFactory(beanFactory);BeanFactory的预准备工作(BeanFactory进行一些设置)
    1)、设置beanFactory的类加载器，支持表达式的解析器....
    2)、添加部分的BeanPostProcessor【ApplicationContextAwareProcessor：bean初始化前后判断是否实现了ApplicationContextAware接口】
    3)、设置忽略的自动装配的接口：EnvironmentAware，EmbeddedValueResolverAware... //默认这些不能通过接口直接注入的
    4)、注册可以解析的自动装配，我们能在任何组件中自动注入：
           - BeanFactory,ResourceLoader,ApplicationEventPublisher,ApplicationContext
    5)、添加 BeanPostProcessor【类型：ApplicationListenerDetector】    
    6)、添加编译时的AspecJ的支持
    7)、给BeanFactory中注册一些能用的组件：
            - environment【ConfigurableEnvironment】环境对象
            - systemProperties【Map<String, Object>】系统环境变量信息
            - systemEnvironment【Map<String, Object>】系统环境信息
4、this.postProcessBeanFactory(beanFactory);BeanFactory准备工作完成后进行的后置处理工作。
    1) 子类通过重写这个方法在BeanFactory创建并预准备完成后作进一步设置(目前是个空方法)
==============================以上是Beanfactory创建及预准备工作===============================================
5、this.invokeBeanFactoryPostProcessors(beanFactory);执行BeanFactoryPostProcessor方法
    - BeanFactoryPostProcessor:BeanFactory的后置处理器，
        在BeanFactory标准初始化之后执行的(前面4步之后)
    - 两个接口：BeanFactoryPostProcessor，BeanDefinitionRegistryPostProcessor
            1) 执行BeanDefinitionRegistryPostProcessor方法  
                1)、获取所有的BeanDefinitionRegistryPostProcessor
                2)、看优先级排序；先执行实现了PriorityOrdered接口的BeanDefinitionRegistryPostProcessor
                执行： postProcessor.postProcessBeanDefinitionRegistry(registry);
                3)、看再执行实现了Ordered顺序接口的BeanDefinitionRegistryPostProcessor ,如果有执行
                     postProcessor.postProcessBeanDefinitionRegistry(registry);
                4)、最后执行没有实现任何优先级或者顺序接口的BeanDefinitionRegistryPostProcessor
            2) 再执行BeanFactoryPostProcessor方法  
                1)、获取所有的BeanFactoryPostProcessor
                2)、下面逻辑跟上边一模一样....不再赘述
6、this.registerBeanPostProcessors(beanFactory)；注册BeanPostProcessors【拦截整个bean的创建过程】
     - BeanPostProcessor：
        - 不同接口类型的BeanPostProcessor，在bean创建前后的执行时机是不一样的
        - DestructionAwareBeanPostProcessor、
          InstantiationAwareBeanPostProcessor、
          SmartInstantiationAwareBeanPostProcessor、
          MergedBeanDefinitionPostProcessor【被放入internalPostProcessors】
            1) 获取所有的BeanPostProcessor；后置处理器默认都可以有PriorityOrdered，Ordered接口来指定优先级
            2)、先注册PriorityOrdered优先级接口的BeanPostProcessor；
                把每一个BeanPostProcessor添加到BeanFactory中
                    beanFactory.addBeanPostProcessor(postProcessor);
            3)、再注册实现了Ordered接口的
            4)、再注册没有实现任何优先级接口的
            5)、最终注册 MergedBeanDefinitionPostProcessor类型的Processor
            6)、注册一个 ；来检查在bean创建完成以后检查是否是ApplicationListener 如果是则
                        this.applicationContext.addApplicationListener((ApplicationListener)bean);
            【以上注册bean的后置处理器：但并不执行】          
         
 7、this.initMessageSource();初始化MessageSource组件(做国际话功能，消息绑定，消息解析)
        1) 获取BeanFactory       
        2) 看容器中是否有id为messageSource，类型为MessageSource组件
               如果有赋值给messageSource，如果没有自己创建一个DelegatingMessageSource
                    MessageSource：取出国际化配置文件中的某个key的值，能按照区域信息获取
        3) 把创建好的MessageSource注册到容器中：以后获取国际化配置文件中的值的时候，可以自动注入MessageSrouce；调用
                【MessageSource.getMessage(String var1, @Nullable Object[] var2, @Nullable String var3, Locale var4);】
            beanFactory.registerSingleton("messageSource", this.messageSource);
        【所以以后要取properties等国际化文件中的key的值，可以直接注入MessageSource对象用getMessage获取即可】 
 8 、this.initApplicationEventMulticaster();初始化事件派发器
        1) 获取beanFactory
        2) 从BeanFactory获取applicationEventMulticaster
                this.applicationEventMulticaster = (ApplicationEventMulticaster)beanFactory.getBean("applicationEventMulticaster", ApplicationEventMulticaster.class);
        3)、如果上一步没有配置。创建一个SimpleApplicationEventMulticaster
        4)、将创建的applicationEventMulticaster注册到BeanFactory容器中；以后其他组件直接自动注入
                beanFactory.registerSingleton("applicationEventMulticaster", this.applicationEventMulticaster);
 9、this.onRefresh();留给子容器(子类)
        1) 子类重写这个方法，在容器刷新的时候可以自定义逻辑      
 10、this.registerListeners();给容器中所有项目中的ApplicationListener注册进去
        1) 从容器中拿到的素有的ApplicationListener
        2) 将每个监听器添加到事件派发器中：
                this.getApplicationEventMulticaster().addApplicationListener(listener);
        3) 派发前步骤产生的事件：直接派发
 11、this.finishBeanFactoryInitialization(beanFactory);
        1) beanFactory.preInstantiateSingletons();初始化剩下的单实例bean
            1) 获取容器中所有的bean，依次进行初始化和创建bean；List<String> beanNames = new ArrayList(this.beanDefinitionNames);
            2) 获取bean的定义信息；RootBeanDefinition
            3) bean不是抽象的，是单实例的，并且不是懒加载的
                    1) 判断当前bean是否是beanFactory：[是否实现了FactoryBean接口的Bean]
                            如果是则利用工厂方法创建bean
                    2) 不是工厂bean，则  利用getBean(beanName)创建Bean；【可以在这里进行打断点，放开判断每个bean的创建情况】     
                            getBean(beanName)；就会调用ioc.getBean();
                            1、this.doGetBean(name, (Class)null, (Object[])null, false);
                            2、先获取缓存中保存的单实例Bean，如果能获取到这个Bean之前被创建过(所有创建过的单实例Bean都会被缓存起来)
                                    private final Map<String, Object> singletonObjects = new ConcurrentHashMap(256);
                                    Object singletonObject = this.singletonObjects.get(beanName);
                            3、缓存中获取不到，开始Bean的创建流程
                            4、标记当前bean已经被创建；this.markBeanAsCreated(beanName);
                            5、获取bean的定义信息【RootBeanDefinition mbd = this.getMergedLocalBeanDefinition(beanName);】
                            6、获取当前bean所依赖的其他bean；【String[] dependsOn = mbd.getDependsOn();】
                                1 我们在bean配置文件中有一个属性depend-on标记他所依赖的其他bean，先加载出来
                                2 如果有按照getBean()将依赖的Bean先创建出来
                            7、启动单实例bean的创建流程
                                1 this.createBean(beanName, mbd, args);创建bean
                                2 beanInstance = this.resolveBeforeInstantiation(beanName, mbdToUse);让BeanPostProcessor先拦截返回代理对象
                                        InstantiationAwareBeanPostProcessor提前执行；
                                        先触发：Object result = ibp.postProcessBeforeInstantiation(beanClass, beanName);
                                        如果有返回值【bean不为空】，触发：current = processor.postProcessAfterInitialization(result, beanName);
                                3 如果前面的InstantiationAwareBeanPostProcessor没有返回代理对象，调用4
                                4 调用beanInstance = this.doCreateBean(beanName, mbdToUse, args);创建Bean
                                        1) 创建bean实例：instanceWrapper = this.createBeanInstance(beanName, mbd, args);
                                             1 利用工厂方法或者对象的构造器创建出bean实例
                                        2) this.applyMergedBeanDefinitionPostProcessors(mbd, beanType, beanName);
                                             拿到所有this.getBeanPostProcessors().iterator();遍历
                                             如果属于MergedBeanDefinitionPostProcessor类型的，
                                                    则调用MergedBeanDefinitionPostProcessor的postProcessMergedBeanDefinition(mbd, beanType, beanName);
                                        3) 为Bean属性赋值: this.populateBean(beanName, mbd, instanceWrapper);
                                              1) 赋值之前：
                                                    1 拿到InstantiationAwareBeanPostProcessor后置处理器
                                                           执行方法： postProcessAfterInstantiation    
                                                    2 拿到InstantiationAwareBeanPostProcessor后置处理器
                                                           执行方法： postProcessPropertyValues
                                                     =======赋值之前，之后才是真正的赋值操作，反射利用setter进行赋值=====================================
                                                    3 应用Bean属性的值：为属性利用setter方法进行赋值
                                                        this.applyPropertyValues(beanName, mbd, bw, (PropertyValues)pvs);
                                        4) 【Bean的初始化】this.initializeBean(beanName, exposedObject, mbd);
                                              1)、【执行Aware接口方法】this.invokeAwareMethods(beanName, bean);执行Aware接口的方法
                                                      1 BeanNameAware、BeanClassLoaderAware、BeanFactoryAware；将这些Aware接口方法执行掉
                                              2)、【执行后置处理器初始化之前的方法】
                                                        ->applyBeanPostProcessorsBeforeInitialization
                                                                ->postProcessBeforeInitialization
                                              3)、【执行初始化方法】 invokeInitMethods
                                                    1、是否是InitiazingBean接口实现，执行接口规定的初始化；
                                                    2、是否自定义初始化方法
                                              4)、【执行初始化之后的方法】applyBeanPostProcessorsAfterInitialization
                                                    processor.postProcessAfterInitialization(result, beanName);
                                        5) 注册Bean的销毁方法；this.registerDisposableBeanIfNecessary(beanName, bean, mbd);
                                        6) 返回create的Bean实例，bean创建完成
                                5) 将创建的bean添加到缓存中：addSingleton[所看源码中暂时没见]
                                ioc容器就是这些Map：很多Map里面保存了单实例bean,环境信息.... 
                                所有Bean都创建完成以后，
                                    检查所有的Bean是否是SmartInitializingSingleton接口的，如果是则执行：
                                        smartSingleton.afterSingletonsInstantiated();方法
                                                                      
 12  this.finishRefresh();完成beanFactory的初始化创建工作，IOC容器就创建完成了
        1)、this.initLifecycleProcessor();初始化和声明周期有关的后置处理器 
                【默认从容器中招是否有LifecycleProcessor的组件，
                    如果没有new DefaultLifecycleProcessor();
                    加入到容器中
                 】
                允许写一个的LifecycleProcessor实现类，可以在BeanFactory
                        void onRefresh();
                        void onClose();//可以在bean工厂在refresh和close的时候调用该定义的方法
        2)、this.getLifecycleProcessor().onRefresh();      
                拿到前面定义的生命周期处理器(BeanFactory);回调 .onRefresh()          
        3)、this.publishEvent；发布容器刷新完成事件   
        4)、 LiveBeansView.registerApplicationContext(this);//略
        
============================================总结====================================================        
 spring源码总结：
     1) spring容器在启动的时候，先会保存所有注册近来的bean的定义信息
         1) xml注册bean <bean>
         2) 注解注册Bean；@Service、@Component、@Bean、xxx
     2) spring容器会在合适时机创建这些bean                                                          
          1) 用到这个bean的时候,利用getBean创建bean，创建好以后保存到容器中
          2) 统一创建剩下的所有bean的时候，finishBeanFactoryInitialization                                       
     3) 后置处理器；
          1) 每个bean创建完成，都会使用各种后置处理器进行处理，来增强bean的功能
                   AutowiredAnnotationBeanPostProcessor:处理自动注入        
                   AnnotationAwareAspectJAutoProxyCreator: 来做AOP功能
                    ....
                    增强功能注解：
                    AsyncAnnotationBeanPostProcessor: 支持异步注解
                    ....
    4) 事件驱动模型
         ApplicationListener：事件监听器
         applicationEventMulticaster派发器：进行事件派发





```                     