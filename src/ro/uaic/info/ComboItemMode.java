package ro.uaic.info;

public class ComboItemMode {
    private String key;
    private int value;

    public ComboItemMode(String key, int value) {
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
