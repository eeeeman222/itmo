package genutilities;

import java.io.Serializable;

public abstract class Element implements Comparable<Element>, Validatable, Serializable {
    abstract public long getId();

    abstract public int getDistance();

}