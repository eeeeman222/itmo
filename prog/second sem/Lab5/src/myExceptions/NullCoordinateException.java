package myExceptions;

public class NullCoordinateException extends Exception{
    public NullCoordinateException()
    {
        super("эта координата не может быть null!(предлагаю ввести значение снова.)");
    }
}
