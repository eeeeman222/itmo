package commands;

import java.util.Objects;

/**
 * Абстрактная команда с именем и описанием
 */
public abstract class Command implements Executable, Describable {
    private final String name;

    private final String description;

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

    /**
     * Выполнить что-либо.
     * @param arguments запрос с данными для выполнения команды
     * @return результат выполнения
     */
    public abstract boolean apply(String[] arguments) throws ClassNotFoundException;

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