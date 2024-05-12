package utilities;

public class Execute {
    private boolean exitCode;
    private String meessage;

    public Execute(boolean code, String s) {
        exitCode = code;
        meessage = s;
    }

    public Execute(String s) {
        this(true, s);
    }

    public boolean getExitCode() { return exitCode; }
    public String getMeessage() { return meessage; }
    public String toString() { return String.valueOf(exitCode)+";" + meessage; }
}