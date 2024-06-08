package servutilities;

import java.io.IOException;
import java.io.PushbackReader;

/**
 * консоль для ввода и вывода
 */
public interface Console {
    void print(Object obj);

    void println(Object obj);

    String readln() throws IOException;

    String filereadln() throws IOException;

    void printError(Object obj);

    void printTable(Object obj1, Object obj2);
    void prompt();
    String getPrompt();
    void selectFileReader(PushbackReader reader);
    void selectConsoleReader();
}