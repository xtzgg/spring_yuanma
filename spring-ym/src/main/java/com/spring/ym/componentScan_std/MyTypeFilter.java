package com.spring.ym.componentScan_std;

import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;

public class MyTypeFilter implements TypeFilter {
    /**
     *
     * @param metadataReader 读取到当前正在扫描的类的信息
     * @param metadataReaderFactory 可以获取到其他任何类信息
     * @return
     * @throws IOException
     */
    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        //true匹配成功，false匹配失败
        //获取当前类注解的信息
        AnnotationMetadata metadata = metadataReader.getAnnotationMetadata();
        //获取当前正在扫描的类的类信息:如：类的类型，实现等等都可以获取
        ClassMetadata classMetadata = metadataReader.getClassMetadata();
        //获取当前类的资源信息(主：类的路径)
        Resource resource = metadataReader.getResource();

        //对以上类信息进行一个打印
        String className = classMetadata.getClassName();//类名
        System.err.println(className);
        String superClassName = classMetadata.getSuperClassName();//父类名
        System.err.println(superClassName);
        String[] interfaceNames = classMetadata.getInterfaceNames();//接口
        for (String interfaceName : interfaceNames) {
            System.err.println("===================");
            System.err.println(interfaceName);
        }
        String[] memberClassNames = classMetadata.getMemberClassNames();//内部类
        for (String memberClassName : memberClassNames) {
            System.err.println("===================");
            System.err.println(memberClassName);
        }
        String enclosingClassName = classMetadata.getEnclosingClassName();//？？
        System.err.println(enclosingClassName);

        //定义一个规则
        if (className.contains("er")){
            return true;
        }//person和componeConfig不属于包扫描的类，本身就是configuration配置信息，所以会输出
        //输出结果包含
        // CController
        //CService
        //myTypeFilter



        return false;
    }
}
