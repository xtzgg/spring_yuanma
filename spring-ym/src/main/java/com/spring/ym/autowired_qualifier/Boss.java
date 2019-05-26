package com.spring.ym.autowired_qualifier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


//默认加载ioc容器的组件，容器启动会调用无参构造器创建对象，再进行初始化赋值等操作
@Component
public class Boss {

    private Keys keys;

    public Keys getKeys() {
        return keys;
    }

    //标注到有参构造器
    //@Autowired
    public Boss( @Autowired  Keys keys){ //标注到参数上
        this.keys = keys;
        System.err.println("Boss....有参构造器");
    }

   // @Autowired //标注方法，spring容器创建当前对象，就会调用方法，完成赋值
                //反复使用的参数，自定义类型从ioc容器中获取
    public void setKeys(Keys keys) {
        this.keys = keys;
    }
}
