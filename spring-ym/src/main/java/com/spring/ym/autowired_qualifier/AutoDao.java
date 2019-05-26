package com.spring.ym.autowired_qualifier;

import org.springframework.stereotype.Repository;

@Repository
public class AutoDao {
    private String label = "1";

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "AutoDao{" +
                "label='" + label + '\'' +
                '}';
    }
}
