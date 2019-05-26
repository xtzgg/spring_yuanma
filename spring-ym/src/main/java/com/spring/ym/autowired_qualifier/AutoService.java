package com.spring.ym.autowired_qualifier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class AutoService {
    //@Qualifier("autoDao2")
    @Autowired(required = false)//指定为false。则不必须
    private AutoDao autoDao;

//    @Autowired
//    private AutoDao autoDao2;

    @Override
    public String toString() {
        return "AutoService{" +
                "autoDao=" + autoDao +
                '}';
    }
}
