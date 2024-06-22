package models;

import genutilities.Validatable;

import java.io.Serializable;
import java.util.Objects;

public class Place implements Validatable, Serializable {
    private long x;
    private Integer y; //Поле не может быть null
    private String name; //Длина строки не должна быть больше 705, Поле не может быть null

    public long getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public String getName() {
        return name;
    }

    public Place(long x, Integer y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    public Place(Integer y, String name) {
        this.y = y;
        this.name = name;
    }
    @Override
    public boolean validate() {
        if (y == null) return false;
        if (name == null || name.isEmpty()) return false;
        if(name.length() > 705) return false;
        return true;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Place place = (Place) o;
        return x == place.x && Objects.equals(y, place.y) && Objects.equals(name, place.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, name);
    }

    @Override
    public String toString() {
        return "Place:" +
                "x=" + x +
                ", y=" + y +
                ", name='" + name + '\'' +
                '}';
    }
}