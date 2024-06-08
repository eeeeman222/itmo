package servmanagers;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.security.NoTypePermission;
import com.thoughtworks.xstream.security.NullPermission;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;
import models.Coordinates;
import models.Location;
import models.Place;
import models.Route;
import servutilities.MyConsole;
import servutilities.Validator;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class ParseManager {

    private static String filename;

    private static XStream xstream;

    public ParseManager(String filename) {
        this.filename = filename;
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
    public void writeCollection(LinkedList<Route> routes) throws IOException {
        xstream.setupDefaultSecurity(xstream);
        xstream.alias("Route", Route.class);
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

    public LinkedList<Route> readCollection()
    {

        xstream.allowTypeHierarchy(Route.class);
        xstream.allowTypeHierarchy(Long.class);
        xstream.allowTypeHierarchy(LinkedList.class);

        xstream.alias("Route", Route.class);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename))))
        {
            List<Route> list = (List<Route>) xstream.fromXML(reader);
            Validator validator = new Validator(list);
            LinkedList<Route> routesLinkedList = new LinkedList<>();
            routesLinkedList.addAll(validator.validate());
            return routesLinkedList;
        }
        catch (IOException e)
        {
            System.err.println("Произошла ошибка при чтении файла " + filename + ": " + e.getMessage());
        }

        return new LinkedList<>();
    }
    @Override
    public String toString() {
        return "FileManager (класс для работы с файлами)";
    }
}