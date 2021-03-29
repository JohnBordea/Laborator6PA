package ro.uaic.info;

import java.awt.*;

public class ComboItemShape {
    private String key;
    private int value;

    public ComboItemShape(String key, int value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return key;
    }

    public String getKey() {
        return key;
    }

    public int getValue() {
        return value;
    }
}
