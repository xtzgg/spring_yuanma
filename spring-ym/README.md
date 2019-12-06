[TOC]
## 学习内容
#### 1 IOC内容 
##### 1.1 组件注册 
- 获取环境变量：
```内容
    - environment.getProperty("animal.height")获取变量
    - `@value`使用：
        - @PropertySource(value={"classpath:animal.properties"},encoding = "utf-8")加载，通过@bean注入对象 @value在注入属性
```
- @bean和@configuration注解注入bean ；对比
- @Scope和@Lazy 
- @ComponentScan 和 @ComponentScans
```可以拦截各种类型，aop也是可以拦截注解的
     FilterType.ANNOTATION  根据注解进行排除
     FilterType.ASSIGNABLE_TYPE 根据给定的类型
     FilterType.REGEX    使用正则表达式指定规则
     FilterType.ASPECTJ  使用ASPECTJ表达式过滤(不常用，不解释)
     FilterType.CUSTOM  使用自定义规则，看源码解释：
        - MyTypeFilter implements TypeFilter
            - params: metadataReader 读取到当前正在扫描的类的信息
              metadataReaderFactory 可以获取到其他任何类信息        
            - return false

```
- @Conditional 条件注入 
``` 类和方法都可以标注
    - @Conditional(WinCondition.class)
        - WinCondition  implements Condition  // 该类无需加注解
        - return boolean
```
- @Import ImportResource ImportBeanDefinationRegister 
```@Import[快速给容器中导入一个组件]
    - @Import(value={Color.class,Read.class,MyImportSelector.class,MyBeanDefinitionRegistry.class})
        - MyImportSelector implements ImportSelector
            - param: AnnotationMetadata: 当前标注@Import注解的类的所有注解信息
            - return new String[]{"com.spring.ym.Import_std.Blue","com.spring.ym.Import_std.Yellew"};
        - MyBeanDefinitionRegistry implements ImportBeanDefinitionRegistrar
            - param： AnnotationMetadata: 当前标注@Import注解的类的所有注解信息
            - param:  BeanDefinitionRegistry 把所有需要添加到容器中的bean添加到beanDefinitionRegistry中，手工注册
            - beanDefinitionRegistry.registerBeanDefinition("rainBow",rootBeanDefinition);
```
- factoryBean管理bean注入
```创建一个spring定义的FactoryBean
    - ColorFactory implements FactoryBean<Color>
    - Object bean = applicationContext.getBean("colorFactory"); //默认获取到的是工厂bean调用getObject创建的对象
    - Object bean1 = applicationContext.getBean("&colorFactory"); // 要获取工厂Bean本身，我们需要给id前面加一个& ：如: &colorFactory
    
```
- postProcessor
```
    - BeanDefinitionRegistryPostProcessor: 
    - MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor,Order

    - BeanFactoryPostProcessor
    - MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor
    
    - BeanDefinitionRegistryPostProcessor方法是在BeanFactoryPostProcessor方法之前执行初始化操作
    - bean尚未被创建
    - BeanDefinitionRegistryPostProcessor 在所有bean定义信息将要被加载，但是bean实例还未创建的时候执行
    - BeanFactoryPostProcessor 在BeanFactory标准初始化之后调用，所有的bean定义已经保存加载到BeanFactory，但是所有的Bean还未创建
    
```
- 发布监听事件
```这里监听ApplicationEvent事件，所有需要加上扫描

    - 监听事件
    - MyApplicationListener implements ApplicationListener<ApplicationEvent> //当该事件触发时调用该方法
    - @EventListener(classes = {ApplicationEvent.class}) //注解到方法上
        - EventListenerMethodProcessor：解析该方法并创建ApplicationListener对象并添加到容器中
    
    - 发布一个事件 //TODO 了解发布事件时机，作用，实际使用方法，根据源码跟踪事件发布监听的过程
        - applicationContext.publishEvent();
        
   拓展：来自于EventListenerMethodProcessor
    - SmartInitializingSingleton原理
        - afterSingletonsInstantiated //根据源码注释：该方法会在所有bean单实例创建完成之后执行
        - 触发时机：
           finishBeanFactoryInitialization;初始化剩下的单实例bean：
              1) 先创建所有的单实例Bean:getBean();
                      beanFactory.preInstantiateSingletons();
              2) 获取所有创建好的单实例bean，判断是否是SmartInitializingSingleton类型的bean，
                  如果是就调用smartSingleton.afterSingletonsInstantiated();
    - ApplicationContextAware：获取Application对象
    - BeanFactoryPostProcessor：获取EventListenerFactory集合.并使用该工厂创建ApplicationListenerMethodAdapter
    
```
- BeanPostProcessor
```在bean初始化前后进行一些处理工作 -> bean此时已经创建完成
    - createBean()
    - BeanPostProcessor.postProcessBeforeInitialization：
    - populateBean(beanName,mdb,instanceWrapper);//初始化赋值
        - @Bean(initMethod = "init",destroyMethod = "destory")
        - @Bean  // Cat implements InitializingBean, DisposableBean
        - @Bean //jsr250  @PostConstruct @PreDestroy
    - BeanPostProcessor.postProcessAfterInitialization:
   
   很多processor 了解每一个的作用
    - ApplicationContextAwareProcessor 获取容器
    - BeanValidationPostProcessor 校验相关
    - BeanNameAutoProxyCreator 
   
   
```
- @Autowired 和 aware
```
    - aware
        - Red implements ApplicationContextAware, BeanNameAware, EmbeddedValueResolverAware
            - BeanNameAware 获取到当前bean的名字(Red)
            - EmbeddedValueResolverAware: StringValueResolver: 解析springel表达式，或者${}等变量的值的解析器
        - 了解各种aware的使用
    - 
    - @Profile 指定在什么环境下注入该bean，也可以注解配置类，该配置类在什么环境下加载
            @Profile("test")
            @Bean("testDataSource")  
    - Autowired
        - @Primary //自动装配首选哪个组件，有该注解，不能再使用@Qualifier
        - @Qualifier("autoDao2") // 指定注入对象优先使用的id
          @Autowired(required = false)//指定为false。则不必须
          private AutoDao autoDao;
```
- 源码追溯
    - 以上组件的作用
    - 触发时机，实际应用场景





###### fork&join







