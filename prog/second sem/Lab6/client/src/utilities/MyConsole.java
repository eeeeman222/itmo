package utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PushbackReader;

public class MyConsole implements Console{
    private static final String P = "...";
    private static PushbackReader fileReader = null;
    /**
     * Выводит obj.toString() в консоль
     * @param obj Объект для печати
     */
    public void print(Object obj) {
        System.out.print(obj);
    }

    public static PushbackReader getFileReader(){
        return fileReader;
    }

    /**
     * Выводит obj.toString() + \n в консоль
     * @param obj Объект для печати
     */
    public void println(Object obj) {
        System.out.println(obj);
    }

    /**
     * Выводит ошибка: obj.toString() в консоль
     * @param obj Ошибка для печати
     */
    public void printError(Object obj) {
        System.err.println("Error: " + obj);
    }

    public String readln() {
        StringBuilder input = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            int c;
            while ((c = reader.read()) != -1) {
                if (c == '\n') {
                    break; // Завершить чтение при обнаружении символа новой строки
                }
                input.append((char) c);
            }
        } catch (IOException e) {
            System.out.println("Ошибка при чтении ввода: " + e.getMessage());
        }
        return input.toString();
    }
    public String filereadln() {
        StringBuilder input = new StringBuilder();

        try {
            int c;
            while ((c = fileReader.read()) != -1) {
                if (c == '\n') {
                    break; // Завершить чтение при обнаружении символа новой строки
                }
                input.append((char) c);
            }
        } catch (IOException e) {
            System.out.println("Ошибка при чтении ввода: " + e.getMessage());
        }
        return input.toString();
    }

    public boolean isCanReadln(PushbackReader reader) {
        int nextChar = 0;
        try {
            nextChar = reader.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if ((nextChar != -1)&&(nextChar != 65535)) {
            try {
                reader.unread(nextChar);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return true;
        } else {
            return false;
        }
    }




    /**
     * Выводит 2 колонки
     * @param elementLeft Левый элемент колонки.
     * @param elementRight Правый элемент колонки.
     */
    public void printTable(Object elementLeft, Object elementRight) {
        System.out.printf(" %-35s%-1s%n", elementLeft, elementRight);
    }

    /**
     * Выводит prompt текущей консоли
     */
    public void prompt() {
        print(P);
    }

    /**
     * @return prompt текущей консоли
     */
    public String getPrompt() {
        return P;
    }


    @Override
    public void selectFileReader(PushbackReader reader) {
        fileReader = reader;
    }

    public void selectConsoleReader() {
        fileReader = null;
    }

}
