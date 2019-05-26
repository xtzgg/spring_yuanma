package com.spring.ym.autowired_qualifier.profilec;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.spring.ym.autowired_qualifier.aware_s.Red;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.StringValueResolver;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 * Profile:
 *         spring为我们提供的可以根据当前环境，动态激活和切换一系列bean的功能
 * 开发环境、测试环境、生产环境：
 * 数据源：(/A)(/B)(/C):
 * @Profile: 指定组件在哪个环境情况下才能被注册到容器当中
 *          不指定，在任何情况下都能注册这个组件
 *  1) 加了环境标识的bean上，只有这个环境被激活的时候才能注册到容器中
 *                  默认环境是 @Profile("default") 不标识也可以
 *  2) 写在配置类上，只有指定环境的时候，整个配置类里面的所有配置才能生效
 *  3) 没有标注环境标识的bean在任何环境都可以加载
 *
 *
 */
//@Profile("test")
@PropertySource({"classpath:/db.properties"})
@Configuration
public class Profile_config  implements EmbeddedValueResolverAware {
    /**
     * 几种获取配置文件中变量的内容方式：
     */
    @Value("${db.username}")  //方式1
    private String username;
    @Value("${db.password}")
    private String password;
    @Value("${db.jdbcUrl}")
    private String jdbcUrl;

    private String driverClassName;
    private StringValueResolver stringValueResolver;

    @Profile("test")
    @Bean
    public Red red(){
        return new Red();
    }

    /**
     * 测试环境数据源
     * @return
     * @throws PropertyVetoException
     */
    @Profile("test")
    @Bean("testDataSource")                           //方式2
    public DataSource dataSourceTest(@Value("${db.password}") String pwd) throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser(username);
        dataSource.setPassword(pwd);
        dataSource.setJdbcUrl("jdbc:mysql://118.24.213.210:3306/air");
       // dataSource.setDriverClass(driverClassName);
        String driverClass = stringValueResolver.resolveStringValue("${db.driverClass}"); //方式3
        dataSource.setDriverClass(driverClass);
        return dataSource;
    }

    /**
     * 开发环境数据源
     * @return
     * @throws PropertyVetoException
     */
    @Profile("dev")
    @Bean("devDataSource")
    public DataSource dataSourceDev() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser(username);
        dataSource.setPassword(password);
        dataSource.setJdbcUrl("jdbc:mysql://118.24.213.210:3306/blog");
        dataSource.setDriverClass(driverClassName);
        return dataSource;
    }

    /**
     * 生产环境数据源
     * @return
     * @throws PropertyVetoException
     */
    @Profile("prod")
    @Bean("prodDataSource")
    public DataSource dataSourceProd() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser(username);
        dataSource.setPassword(password);
        dataSource.setJdbcUrl("jdbc:mysql://118.24.213.210:3306/sell");
        dataSource.setDriverClass(driverClassName);
        return dataSource;
    }

    @Override
    public void setEmbeddedValueResolver(StringValueResolver stringValueResolver) {
            this.stringValueResolver = stringValueResolver;
            this.driverClassName = stringValueResolver.resolveStringValue("${db.driverClass}");
    }
}
