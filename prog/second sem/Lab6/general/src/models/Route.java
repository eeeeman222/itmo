package models;

import genutilities.Element;
import genutilities.Validatable;

import java.time.LocalDateTime;
import java.util.Objects;

public class Route extends Element implements Validatable {
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Location from; //Поле не может быть null
    private Place to; //Поле может быть null
    private int distance; //Значение поля должно быть больше 1

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Location getFrom() {
        return from;
    }

    public Place getTo() {
        return to;
    }

    public int getDistance() {
        return distance;
    }


    public Route(long id, String name, Coordinates coordinates, LocalDateTime creationDate, Location from, Place to, int distance) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.from = from;
        this.to = to;
        this.distance = distance;
    }


    public Route(long id, String name, Coordinates coordinates, Location from, Place to, int distance) {
        this(id, name, coordinates, LocalDateTime.now(), from, to, distance);
    }
    @Override
    public boolean validate() {
        if (id <= 0) return false;
        if (name == null || name.isEmpty()) return false;
        if (creationDate == null) return false;
        if (coordinates == null || !coordinates.validate()) return false;
        if (from == null || !from.validate()) return false;
        if (to != null && !to.validate()) return false;
        if (distance <= 1) return false;
        return true;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return id == route.id && distance == route.distance && Objects.equals(name, route.name) && Objects.equals(coordinates, route.coordinates) && Objects.equals(creationDate, route.creationDate) && Objects.equals(from, route.from) && Objects.equals(to, route.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, from, to, distance);
    }

    @Override
    public String toString() {
        return "Route{" +
                "id=" + id + '\n' +
                " name='" + name + '\'' + '\n' +
                " coordinates=" + coordinates + '\n' +
                " creationDate=" + creationDate + '\n' +
                " from=" + from + '\n' +
                " to=" + to + '\n' +
                " distance=" + distance + '\n' +
                '}';
    }

    @Override
    public long getId() {
        return id;
    }

    public void setId(long newId){
        this.id = newId;
    }
    @Override
    public int compareTo(Element element) {
        return (this.distance - element.getDistance());
    }
}
