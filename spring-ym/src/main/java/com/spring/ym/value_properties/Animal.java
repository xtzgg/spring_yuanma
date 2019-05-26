package com.spring.ym.value_properties;


import org.springframework.beans.factory.annotation.Value;

public class Animal {
    //三种赋值方法
    //1 直接赋值基本属性
    //2 #{} //springEL表达式
    //3 ${} //从配置文件[properteis文件]中读取变量，并赋值给该属性

    @Value("张三")
    private String name;
    @Value("#{20-2}")
    private Integer age;
    @Value("${animal.height}")
    private String height;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", height=" + height +
                '}';
    }
}
