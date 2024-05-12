package managers;

import com.thoughtworks.xstream.io.xml.StaxDriver;
import models.*;
import utilities.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.NoTypePermission;
import com.thoughtworks.xstream.security.NullPermission;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;
import utilities.Console;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ParseManager {

    private static String filename;

    private static XStream xstream;
    private static MyConsole console;

    public ParseManager(String filename, MyConsole console) {
        this.filename = filename;
        this.console = console;
        xstream = new XStream(new StaxDriver());

        xstream.alias("Route", Route.class); //передаем названия классов.
        xstream.alias("Coordinates", Coordinates.class);
        xstream.alias("Location", Location.class);
        xstream.alias("Place", Place.class);
        xstream.alias("routes", CollectionManager.class);
        xstream.addImplicitCollection(CollectionManager.class, "collection");

        xstream.setMode(XStream.NO_REFERENCES);
        xstream.addPermission(NoTypePermission.NONE);
        xstream.addPermission(NullPermission.NULL);
        xstream.addPermission(PrimitiveTypePermission.PRIMITIVES);
        xstream.allowTypeHierarchy(List.class);
        xstream.allowTypeHierarchy(String.class);
    }
    public static void writeCollection(LinkedList<Route> routes) throws IOException {
        xstream.setupDefaultSecurity(xstream);
        xstream.alias("Route", models.Route.class);
        PrintWriter writer = new PrintWriter(new FileWriter(filename));
        writer.write(xstream.toXML(routes));
        writer.flush();
    }
    /**public static void writeCollection(LinkedList<Route> collection) {
        if (!filename.equals("")) {
            try {
                PrintWriter collectionFileWriter = new PrintWriter(filename);

                String xml = xstream.toXML(new ArrayList<>(collection)); // сериализуется список элементов коллекции в формат XML и сохраняется в строковую переменную xml
                byte[] buffer = xml.getBytes(); // создается массив байтов buffer из строки xml
                collectionFileWriter.write(Arrays.toString(buffer), 0, buffer.length);
                collectionFileWriter.flush(); // все накопленные данные записываются в файл

                MyConsole.println("Сохранение в файл прошло успешно");
            } catch (IOException exception) {
                MyConsole.printError("Файл недоступен к открытию");
            }
        } else MyConsole.printError("ошибка при записи");
    }
     */

    public static LinkedList<Route> readCollection()
    {

        xstream.allowTypeHierarchy(models.Route.class);
        xstream.allowTypeHierarchy(java.lang.Long.class);
        xstream.allowTypeHierarchy(java.util.LinkedList.class);

        xstream.alias("Route", models.Route.class);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename))))
        {
            List<Route> list = (List<Route>) xstream.fromXML(reader);
            Validator validator = new Validator(list);
            LinkedList<Route> routesLinkedList = new LinkedList<>();
            console.println("загрузка данных из файла. невалидные элементы не будут загружены");
            routesLinkedList.addAll(validator.validate());
            return routesLinkedList;
        }
        catch (IOException e)
        {
            System.err.println("Произошла ошибка при чтении файла " + filename + ": " + e.getMessage());
        }

        return new LinkedList<>();
    }

    public static String path;

    public static String getName(){
        try {
            path = System.getenv("lab5"); // lab5 - полный путь до файла, включая его название
            String[] checkPaths = path.split(";");
            if (checkPaths.length > 1) {
                console.println("В этой переменной содержится более одного пути к файлам.\nПрограмма остановлена.");
                System.exit(0);
            }
        } catch (NullPointerException e) {
            console.printError("Некорректная переменная окружения.\nПрограмма остановлена.");
            System.exit(0);
        }
        File name = new File(path);
        return name.getName();
    }
    @Override
    public String toString() {
        return "FileManager (класс для работы с файлами)";
    }
}