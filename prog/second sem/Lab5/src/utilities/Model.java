package utilities;

public abstract class Model implements Validatable {
    abstract public long getId();

    abstract public int compareTo(Model model);
}
