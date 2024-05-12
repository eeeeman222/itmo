package myExceptions;

public class WrongDistanceException extends Exception{
    public WrongDistanceException(int x)
    {
        super("в моем варианте distance > 1, а сейчас вы ввели %s.(предлагаю ввести новое значение для distance)".formatted(x));
    }
}
