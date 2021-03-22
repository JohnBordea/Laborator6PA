package ro.uaic.info;

import java.awt.*;

public class ComboItem {
    private String key;
    private Color value;

    public ComboItem(String key, Color value) {
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
