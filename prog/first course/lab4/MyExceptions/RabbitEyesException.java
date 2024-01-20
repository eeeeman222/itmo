package MyExceptions;

public class RabbitEyesException extends Exception {
    public RabbitEyesException(String color)
    {
        super("кролик должен быть с розовыми глазами,а сейчас кролик с цветом глаз %s".formatted(color));
    }
}
