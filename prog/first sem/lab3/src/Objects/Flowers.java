package Objects;

public class Flowers{
    public static String name;
    @Override
    public String toString() {
        return name;
    }
    public Flowers(String name) {
        this.name = name;
    }
}
