package com.spring.ym.componentScan_std;

import org.springframework.stereotype.Repository;

@Repository
public class CRepository {

    private String label = "1";

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
