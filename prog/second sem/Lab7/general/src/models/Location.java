package models;

import genutilities.Validatable;

import java.io.Serializable;
import java.util.Objects;

public class Location implements Validatable, Serializable {
    private Integer x; //Поле не может быть null
    private double y;
    private String name; //Поле может быть null

    public Location(Integer x, double y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    public Location(Integer x, String name) {
        this.x = x;
        this.name = name;
    }

    @Override
    public boolean validate() {
        if (x == null) return false;
        if (name == null || name.isEmpty()) return false;
        return true;
    }

    public Integer getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Double.compare(y, location.y) == 0 && Objects.equals(x, location.x) && Objects.equals(name, location.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, name);
    }

    @Override
    public String toString() {
        return "Location{" +
                "x=" + x +
                ", y=" + y +
                ", name='" + name + '\'' +
                '}';
    }
}
