package ro.uaic.info;

import java.awt.*;
import java.sql.Struct;

public class CreatedShape {
    private int x, y, radius, sides, type;

    private Color color;

    public CreatedShape(int x, int y, int radius, int sides, Color color, int type) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.sides = sides;
        this.color = color;
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getRadius() {
        return radius;
    }

    public int getSides() {
        return sides;
    }

    public int getType() {
        return type;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return switch (type) {
            case 0 -> "Free";
            case 1 -> "Circle";
            case 2 -> "Square";
            default -> "";
        };
    }
}
