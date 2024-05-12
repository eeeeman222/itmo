package MyExceptions;

public class SpacePocketException extends Exception{
    public SpacePocketException(String clock)
    {
        super("в кармане нет часов");
    }
}
