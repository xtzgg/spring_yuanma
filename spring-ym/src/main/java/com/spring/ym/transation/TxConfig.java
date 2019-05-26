package com.spring.ym.transation;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 * 声明式事务:
 *
 *  环境搭建：
 *     1 导入相关依赖
 *          数据源、数据库驱动、spring-jdbc模块
 *     2 配置数据源、jdbcTemplate(spring提供简化数据库操作的工具)操作数据库
 *     3 给方法标注@Transactional表示当前方法是一个事务的方法
 *     4 @EnableTransactionManagement开启基于注解的事务管理功能
 *              @Enablexxxx
 *              此时启动报错：No qualifying bean of type 'org.springframework.transaction.PlatformTransactionManager' 事务管理器也需要配置
 *     5 配置事务管理器来控制事务
 *
 *原理):
 *      1) @EnableTransactionManagement
 *          TransactionManagementConfigurationSelector给容器中导入两个组件
 *                  AutoProxyRegistrar
 *                 ProxyTransactionManagementConfiguration  //后置处理器包装代理对象
 *          注解里面包含了.默认： AdviceMode mode() default AdviceMode.PROXY;
 *      2）AutoProxyRegistrar：
 *              给容器中中注册了一个InfrastructureAdvisorAutoProxyCreator组件
 *                  跟AOP一模一样：利用后置处理器机制在对象创建以后，包装对象，返回一个代理对象(增强器)，代理对象执行方法利用拦截器链进行调用
 *      3) ProxyTransactionManagementConfiguration
 *              1、给容器中注册事务增强器
 *                     1）事务增强器要用事务注解的信息：解析事务注解
 *                     2）事务拦截器：
 *                                  ：保存了事务的属性信息，事务管理器；
 *                           它是一个MethodInterceptor：
 *                              在目标方法执行的时候，
 *                                      执行拦截器链
 *                                      事务拦截器：
 *                                          1)、先获取事务的属性
 *                                          2)、获取PlatformTransactionManager
 *                                                  ：指定事务管理器的名字(一般不指定)
 *                                                  ：如果事先没有添加指定任何transactionManager，会从容器中按照类型获取一个PlatformTransactionManager
 *                                          3)、执行目标方法
 *                                                  如果异常，获取到事务管理器，利用事务管理器回滚这次操作
 *                                                  如果正常，利用事务管理提交事务
 *
 *
 */
@EnableTransactionManagement
@Configuration
@ComponentScan("com.spring.ym.transation")
public class TxConfig {
    @Bean
    public DataSource dataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser("root");
        dataSource.setPassword("oa#123Jll.li");
        dataSource.setJdbcUrl("jdbc:mysql://118.24.213.210:3306/air");
        dataSource.setDriverClass("com.mysql.jdbc.Driver");
        return dataSource;
    }
    @Bean
    public JdbcTemplate jdbcTemplate() throws PropertyVetoException {
        //spring对configuration类会特殊处理，给容器中加组件的方法，多次调用只是从容器中找组件
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource());
        return jdbcTemplate;
    }
    @Bean //注册事务管理器到容器中
    public PlatformTransactionManager transactionManager() throws PropertyVetoException {
        return new DataSourceTransactionManager(dataSource());
    }
}
