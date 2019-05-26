package com.spring.ym.Import_std;

import com.spring.ym.conditional_std.WinCondition;
import com.spring.ym.entity.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Conditional(WinCondition.class)
@Import(value={Color.class,Read.class,MyImportSelector.class,MyBeanDefinitionRegistry.class})   //com.spring.ym.Import_std.Color
//@import导入组件，id默认是组件的全类名(id即为bean注解的引用)
public class Import_config {
    @Bean
    public Person person(){
        return new Person();
    }
}
