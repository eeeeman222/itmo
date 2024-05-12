package Objects;

public class Wreath {
    public static String name;
    @Override
    public String toString() {
        return name;
    }
    public Wreath(String name) {
        this.name = name;
    }
}
