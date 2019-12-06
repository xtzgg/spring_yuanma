package com.spring.ym.IOC_total;

import org.springframework.stereotype.Component;

@Component
public class TowBlue {
    private RestBlue restBlue;
    public TowBlue() {
        System.out.println("TowBlue====noParams");
    }
    public TowBlue(RestBlue restBlue) {
        System.out.println("TowBlue====hasParams");
        this.restBlue = restBlue;
    }
}
