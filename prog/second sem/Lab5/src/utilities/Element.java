package utilities;

public abstract class Element implements Comparable<Element>, Validatable{
    abstract public long getId();

    abstract public int getDistance();

}