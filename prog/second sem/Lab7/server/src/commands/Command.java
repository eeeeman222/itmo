package commands;

import requests.*;
import responses.*;
import servmanagers.CollectionManager;

import java.sql.SQLException;
import java.util.Objects;

/**
 * Абстрактная команда с именем и описанием.
 */
public abstract class Command implements Executable, Describable {
    private final String name;

    private final String description;

    private CollectionManager collectionManager;

    public Command(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public boolean resolve(String name) {
        return name.equals(this.name);
    }

    public String getDescription() {
        return description;
    }

    /**
     * @return Название и использование команды.
     */
    public String getName() {
        return name;
    }

    public abstract Response apply(Request request) throws SQLException;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Command command = (Command) o;
        return Objects.equals(name, command.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Command{" +
                "name='" + name + '\'' +
                '}';
    }
}