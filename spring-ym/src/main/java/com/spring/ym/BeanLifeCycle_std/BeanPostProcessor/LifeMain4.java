package com.spring.ym.BeanLifeCycle_std.BeanPostProcessor;

/**
 * 4) BeanPostProcessor : bean的后置处理器
 *      在bean初始化前后进行一些处理工作
 *          a BeanPostProcessor.postProcessBeforeInitialization：
 *                  bean建立之后，任何初始化方法：如：init-method  postConstruct等 执行之前执行
 *          b BeanPostProcessor.postProcessAfterInitialization:
 *                  在任何初始化方法执行之后进行执行
 *          任何一个bean初始化之前之后都会调用该before和after方法(几个bean调用几次)
    // 16课时 分析processor执行源码 通过debug方式 分析得到：(忘记看视频)
           a 遍历容器中所有的beanPostProcessor，挨个执行beforeInitialization
        一旦返回null，跳出for循环，不会执行后边的beanPostProcessor
           b 所有执行初始化方法都是在populateBean(beanName,mdb,instanceWrapper)之后执行的，该方法就是给bean
        进行初始化和属性赋值操作（Properties等获取并赋值）
 spring底层对 BeanPostProcessor 的使用
        1 如实现类1 ：ApplicationContextAwareProcessor ：可以通过实现该接口获取spring容器对象
        2 校验参数类：BeanValidationPostProcessor
        //初始化前后校验参数
                 if (this.afterInitialization) {
                 this.doValidate(bean);
                 }
                 if (!this.afterInitialization) {
                 this.doValidate(bean);
                 }
        3 调用初始化方法：如init-method 和@postConstructor注解执行类
                InitDestroyAnnotationBeanPostProcessor中的方法
                        postProcessBeforeInitialization方法循环执行对应的bean的初始化方法：通过element.invoke(target)执行；这也是标注@postContruct
                    spring如何执行该初始化方法的
        4 bean的赋值，注入其他组件 @Autowired 生命周期注解功能 ：@Async,xxx BeanPostProcessor;
                @Autowired  处理processor类：AutowiredAnnotationBeanPostProcessor

 */
public class LifeMain4 {
}
