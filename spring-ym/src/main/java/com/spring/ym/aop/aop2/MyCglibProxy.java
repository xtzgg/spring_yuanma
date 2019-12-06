package com.spring.ym.aop.aop2;
 
import java.lang.reflect.Method;
 
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

class Father{
    public void sayHello(){
        System.out.println("this is father");
    }
}
 
interface Person{
    void speak();
    void walk();
}
 
class Student extends Father implements Person{
    public void study(){
        System.out.println("i am student.");
    }
     
    @Override
    public void speak() {
        System.out.println("i am student ,i can speak");
    }
 
    @Override
    public void walk() {
        System.out.println("i am student ,i can walk");       
    }
}
 
class MyMethodInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object obj, Method method, Object[] arg, MethodProxy proxy) throws Throwable {
        System.out.println("Before: "+ method);
        Object object = proxy.invokeSuper(obj, arg);
        System.out.println("After: "+method);
        return object;
    }
     
}
 
public class MyCglibProxy {
    public static void main(String[] args) {
        //可以指定 CGLIB 将动态生成的代理类保存至指定的磁盘路径下
        //System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY,"本地磁盘路径");
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Student.class);
        enhancer.setCallback(new MyMethodInterceptor());
        Student student = (Student)enhancer.create();
        student.sayHello();
        student.speak();
        student.walk();
        student.study();
    }
     
}