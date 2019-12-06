package com.spring.ym.IOC_total;

import org.springframework.stereotype.Component;

@Component
public class RestBlue {
    private TowBlue towBlue;
//    public RestBlue(){
//        System.out.println("RestBlue====noParams");
//    }
    public RestBlue(TowBlue towBlue) {
        System.out.println("RestBlue====hasParams");
        this.towBlue = towBlue;
    }
}
