package com.spring.ym.Import_std;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;
//自定义逻辑需要返回导入的组件：将该组件加入到@Import中，可以自定义返回组件
public class MyImportSelector implements ImportSelector {
    //返回值，就是要导入大哦容器中的组件的全类名
    //AnnotationMetadata: 当前标注@Import注解的类的所有注解信息
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {

        //返回空数组也不要返回null，不然会空指针异常
        //return new String[0];
        //注入组件,必须写全类名
        return new String[]{"com.spring.ym.Import_std.Blue","com.spring.ym.Import_std.Yellew"};
    }
}
