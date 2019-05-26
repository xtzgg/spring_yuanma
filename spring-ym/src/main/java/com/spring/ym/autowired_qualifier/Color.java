package com.spring.ym.autowired_qualifier;

public class Color
{
    private Keys keys;

    public Keys getKeys() {
        return keys;
    }

    public void setKeys(Keys keys) {
        this.keys = keys;
    }

    @Override
    public String toString() {
        return "Color{" +
                "keys=" + keys +
                '}';
    }
}
