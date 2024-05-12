package models;

import utilities.Validatable;

import java.util.Objects;

public class Coordinates implements Validatable {
    private Long x; //Поле не может быть null
    private Float y; //Максимальное значение поля: 56, Поле не может быть null

    public Coordinates(Long x, Float y){
        this.x = x;
        this.y = y;
    }
    public Long getX(){
        return x;
    }

    public Float getY() {
        return y;
    }

    @Override
    public boolean validate(){
        return x != null && y != null && y <= 56;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return Double.compare(x, that.x) == 0 && y == that.y;
    }
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}