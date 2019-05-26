package com.spring.ym.autowired_qualifier;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**                                     20节课
 * 自动装配
 * 1)、@Autowired，自动注入：
 *      1).默认优先按照类型去容器中招相对应的组件，applicationContext.getBean(BookDao.class);
 *      2).如果找到多个相同类型的组件，再将属性的名称作为组件的id去容器中查找
 *                  applicationContext.getBean("bookDao");
 *      3) @Qualifier("bookDao"); 使用@Qualifier明确指定需要装配的组件的id，而不再使用属性名
 *      4) 自动装配默认一定要将属性赋值好，没有就报错
 *              可以使用@Autowired(@Required=false)来指定不必须装配
 *      5) 自动装配首选哪个bean 可以使用@Primary ;此时不能使用@Qualifier
 *                 此时与指定的Autowired注入的bean id无关
 *                 此时如果使用@Qualifier来指定bean，则@Primary无效
 *
 *  //该说明了@Resource和@Autowired的区别     21节课
 * 2)、Spring还支持@Resource（JSR250）注解 和@Inject(JSR330) //java规范
 *          @Resource：将@Autowired替换成该注解也能完成自动装配功能
 *                      默认按照组件名称作为组件装配
 *                      也可以通过添加(name="组件名")指定注入对象名
 *                      没有能够支持@primary功能；没有支持@Autowired(required=false)的功能；
 *          @Inject：
 *                  该注解需要导入一个依赖javax.inject包 (maven)才能生效
 *                  和autowired功能一样：但是这里没有required=false
 *  区别：
 *          autowired是spring定义的，//不能脱离spring框架存在
 *          resource和inject都是java规范
 *      AutowiredAnnotationBeanPostProcessor:
 *                  该类完成autowired的自动装配
 *
 * 3) @Autowired: 使用位置
 *          构造器，参数，方法，属性都可以使用该注解标注；都是从容器中获取参数组件的值
 *          1 标注到方法上  @Bean + 方法参数；参数从容器中获取；默认不写Autowired效果是一样的
 *          2 标注在构造器上(参数从容器中获取)
 *                  如果组件只有一个有参构造器，这个有参构造器的@Autowired可以省略，参数位置的组件还可以自动从容器中获取
 *          3 标注到参数上(该参数还是从容器中获取)
 *
 *  4) 自定义组件：想要使用Spring容器底层的一些组件(ApplicationContext,BeanFactory....)
 *          自定义组件实现XXXAware；在创建对象的时候，会调用接口规定的方法注入spring底层的相关组件
 *          把spring底层的一些组件注入到自定义的Bean中
 *          原理：
 *          XXXAware: 功能使用xxxProcessor来处理:
 *              ApplicationContextAware===>ApplicationContextAwareProcessor
 *              该processor类中对应后置处理器的方法，判断是否属于ApplicationContextAware的bean
 *          如果是则执行set方法进行赋值操作，将容器对象赋值给自定义类
 *               (如：环境变量对象或者beanName进行对应spring底层对象的赋值操作)
 *
 */
public class Autowire_Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Autowired_config.class);
        AutoService autoService = (AutoService)applicationContext.getBean("autoService");
        System.err.println(autoService.toString());
//        AutoDao bean = applicationContext.getBean(AutoDao.class);
//        System.err.println(bean.toString());
        AutoDao autoDao = (AutoDao)applicationContext.getBean("autoDao");
        System.err.println(autoDao.toString());
        Keys bean = applicationContext.getBean(Keys.class);
        System.err.println(bean);
        Object keys = applicationContext.getBean("keys");
        System.err.println(keys);
    }
}
