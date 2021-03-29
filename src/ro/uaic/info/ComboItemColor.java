package ro.uaic.info;

import java.awt.*;

public class ComboItemColor {
    private String key;
    private Color value;

    public ComboItemColor(String key, Color value) {
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

    public Color getValue() {
        return value;
    }
}
