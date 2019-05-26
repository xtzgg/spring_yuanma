package com.spring.ym.autowired_qualifier;

import com.spring.ym.componentScan_std.CRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@ComponentScan({"com.spring.ym.autowired_qualifier"})
public class Autowired_config {
    @Primary //自动装配首选哪个组件，有该注解，不能再使用@Qualifier
    @Bean("autoDao2")
    public AutoDao cRepository(){
        AutoDao autoDao = new AutoDao();
        autoDao.setLabel("2");
        return autoDao;
    }

    /**
     * @Bean 标注 的方法创建对象的时候，方法参数的值从容器中获取
     * @param keys
     * @return
     */
    @Bean
    public Color color(Keys keys){ //@autowired可以省略
        return new Color();
    }

//    @Bean
//    public Color color(@Autowired Keys keys){ //两者效果是一样的
//        return new Color();
//    }
}
