package com.spring.ym.executePrinciple;

import com.spring.ym.Import_std.Blue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 扩展原理：
 * 1、BeanFactoryPostProcessor
 *      BeanPostProcessor: bean的后置处理器：bean创建对象初始化前后进行拦截工作的
 *      BeanFactoryPostProcessor：beanFactory的后置处理器
 *              在BeanFactory标准初始化之后调用，所有的bean定义已经保存加载到BeanFactory，但是所有的Bean还未创建
 *  1) ioc容器创建对象
 *  2) invokeBeanFactoryPostProcessors(beanFactory);执行BeanFactoryPostProcessor；
 *          找到所有的BeanFactoryPostProcessor，并执行他们的方法
 *              1) 直接在beanFactory中找到所有类型是BeanFactoryPostProcessor的组件，并执行他们的方法
 *                          while(var2.hasNext()) {
                                  BeanFactoryPostProcessor postProcessor = (BeanFactoryPostProcessor)var2.next();
                                  postProcessor.postProcessBeanFactory(beanFactory);
                             }
 *              2) 在初始化创建其他组件前面执行
 *
 *2、BeanDefinitionRegistryPostProcessor
 *      postProcessBeanDefinitionRegistry()
 *          根据文档注释：在所有bean定义信息将要被加载，但是bean实例还未创建的时候执行（在beanFactoryPostProcessor之前执行）
 *      优先于BeanFactoryPostProcessor执行，利用BeanDefinitionRegistryPostProcessor再给容器中额外添加一些组件
 *  原理：
 *      1)、ioc容器创建
 *      2)、refresh()方法->invokeBeanFactoryPostProcessors(beanFactory);
 *      3)、获取到BeanDefinitionRegistryPostProcessor类型的所有组件，
 *              【以下两个都是针对该类型的bean执行，至于后来的BeanFactoryPostProcessor类的加载方法，需要往后排】
 *                  1、依次触发所有的postProcessBeanDefinitionRegistry方法(前面还有优先级排序)
 *                  2、再次触发postProcessBeanFactory()方法
 *      4)、然后拿到BeanFactoryPostProcessor类型的所有组件，按照优先级进行排序，然后依次触发postProcessBeanFactory()方法
 *
 * 3、ApplicationListener:监听容器中发布的事件，事件驱动模型的开发
 *          public interface ApplicationListener<E extends ApplicationEvent> //泛型就是我们需要监听的事件
 *              监听ApplicationEvent及其下面的子事件
 *     自定义监听器：
 *      步骤：
 *          1)、写一个监听器来监听某个事件(ApplicationEvent及其子类)
 *              或者 注解 @EventListener
 *                      ：下面会重点说该注解的原理
 *          2)、把监听器加入到容器中
 *          3)、只要容器中有相关事件的发布，我们就能监听到这个事件
 *                  ContextRefreshedEvent：容器刷新完成(所有Bean都完全创建)会发布这个事件
 *                  ContextClosedEvent：关闭容器会发布这个事件
 *          4)、发布一个事件
 *                  applicationContext.publishEvent();
 *   原理：
 *      ContextRefreshedEvent
 *      ExMain$1[source=发布一个事件]
 *      ContextClosedEvent
 *      1)、ContextRefreshedEvent事件：
 *              1)、容器创建对象：refresh();
 *              2)、finishRefresh();容器刷新完成会发布一个ContextRefreshedEvent事件
 *      2)、自己发布一个事件
 *      3)、容器关闭会发布ContextClosedEvent：
 *
 *       【事件发布流程】
 *              3)、this.publishEvent((ApplicationEvent)(new ContextRefreshedEvent(this)));
 *                      事件发布流程：
 *                          1) 获取事件的多播器(派发器)：getApplicationEventMulticaster();
 *                          2) multicastEvent 派发事件
 *                          3) 获取到所有的ApplicationListener
 *                                while(var4.hasNext()) { 遍历ApplicationListener操作
 *                                  1)、如果有executor，可以支持使用executor进行异步派发
 *                                      Executor executor = this.getTaskExecutor();
 *                                  2)、否则，同步的方法直接执行listener方法；
 *                                         拿到listener回调onApplicationEvent方法
     *                                      -> this.invokeListener(listener, event);
     *                                          -> onApplicationEvent
 * 【事件多播器(派发器)】
 *      1)、容器创建对象，调用refresh方法
 *      2)、在其他bean创建之前，初始化 this.initApplicationEventMulticaster();
 *          1)、先去容器中招有没有id="applicationEventMulticaster"的组件
 *          2)、如果没有this.applicationMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
 *          并且加入到容器中，我们就可以在其他组件要派发事件，自动注入这个applicationMulticaster
 *
 *  【容器中有哪些监听器】
 *      1)、容器创建对象：refresh();
 *  *   2)、this.registerListeners();
 *              从容器中拿到所有的监听器，把他们呢注册到applicationEventMulticaster中：
 *              String[] listenerBeanNames = this.getBeanNamesForType(ApplicationListener.class, true, false);
 *              //将listener注册到applicationEventMulticaster当中
 *                  this.getApplicationEventMulticaster().addApplicationListenerBean(listenerBeanName);

    4 @EventListener 该注解使用也非常方便
            【源码注释说看该类：EventListenerMethodProcessor】
            原理：使用EventListenerMethodProcessor处理器来解析方法上的@EventListener
            1、SmartInitializingSingleton原理
                    afterSingletonsInstantiated //根据源码注释：该方法会在所有bean单实例创建完成之后执行
                                                    //类似于：ContextClosedEvent
            2、触发时机：
            【debug查看】
                1) ioc容器创建对象并refresh();
                2) finishBeanFactoryInitialization;初始化剩下的单实例bean：
                    1) 先创建所有的单实例Bean:getBean();
                            beanFactory.preInstantiateSingletons();
                    2) 获取所有创建好的单实例bean，判断是否是SmartInitializingSingleton类型的bean，
                        如果是就调用smartSingleton.afterSingletonsInstantiated();


 */
@ComponentScan("com.spring.ym.executePrinciple")
@Configuration
public class ExtConfig {
    @Bean
    public Blue blue(){
        System.err.println("blue----new one");
        return new Blue();
    }
}
