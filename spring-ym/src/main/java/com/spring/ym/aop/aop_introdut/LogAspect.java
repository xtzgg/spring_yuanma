package com.spring.ym.aop.aop_introdut;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.Arrays;

/**
 * 加上@Aspect注解：告诉spring容器哪个是切面类
 */
@Aspect
public class LogAspect {
    //公共切入点表达式
    //1、本类引用：直接写方法名
    //2、其他切面类引用：写方法全名
    // @Pointcut 表达式写法非常多，参照spring官方文档即可
    @Pointcut("execution(public int com.spring.ym.aop.aop_introdut.MathCaculator.*(..))")
    public void pointCut(){

    }


    //@Before在目标方法之前切入，切入点表达式(指定在哪个方法切入)
    //public int com.spring.ym.aop.aop_introdut.MathCaculator.*(..)
    //@Before("public int com.spring.ym.aop.aop_introdut.MathCaculator.div(int,int)")
/*    @Before("pointCut()") //本类引用示例：直接写方法名
    public void logStart(){
        System.out.println("出发运行--Before--参数列表是：{}");
    }*/
   // @After("public int com.spring.ym.aop.aop_introdut.MathCaculator.*(..)")
    @After("com.spring.ym.aop.aop_introdut.LogAspect.pointCut()")
    public void logEnd(){
        System.out.println("出发结束 After");
    }
  /*  @AfterReturning("pointCut()")
    public void logReturn(){
        System.out.println("除法正常返回. AfterReturning...运行结果是：{}");
    }*/
/*    @AfterThrowing("pointCut()")
    public void logException(){
        System.out.println("除法异常，异常信息--AfterThrowing---：{}");
    }*/
//----------------拿到方法名和参数内容---------------------------------------------
        @Before("pointCut()") //本类引用示例：直接写方法名
        public void logStart(JoinPoint joinPoint){
            //参数列表
            Object[] args = joinPoint.getArgs();
            System.out.println("出发运行--Before------方法名："+ joinPoint.getSignature().getName() +"----------参数列表是：{"+ Arrays.asList(args) +"}");
        }
//----------------拿到返回结果-------------------------
    //joinPoint 一定放到参数的首位，否则报错
    @AfterReturning(value = "pointCut()",returning="result")
    public void logReturn(JoinPoint joinPoint,Object result) {
        System.out.println(joinPoint.getSignature().getName()+"正常返回. AfterReturning...运行结果是：{" + result + "}");
    }
    //------------拿到异常结果--------------------------------
    @AfterThrowing(value = "pointCut()",throwing = "ex")
    public void logException(JoinPoint joinPoint,Exception ex){
        System.out.println(joinPoint.getSignature().getName()+"异常，异常信息--AfterThrowing---：{"+ ex +"}");
    }
}
