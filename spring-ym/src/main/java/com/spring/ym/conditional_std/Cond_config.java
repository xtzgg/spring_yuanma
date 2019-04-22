package com.spring.ym.conditional_std;

import com.spring.ym.entity.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Conditional({LinuxCondition.class}) //满足当前条件，这个类中配置的所有bean注册才能生效
@Configuration
public class Cond_config {
    /**
     * @Conditional 按照一定的条件进行判断，满足条件容器中注册bean
     *
     * 1 实现目标：
     *      当环境是window时，输出小叶
     *      当环境时linux时，输出小红
     */
    @Conditional({WinCondition.class}) //类和方法都可以标注
    @Bean("ye")
    public Person person01(){
        return new Person(13,"小叶");
    }
    @Conditional({LinuxCondition.class})
    @Bean("hong")
    public Person person02(){
        return new Person(15,"小红");
    }
}
