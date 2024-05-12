package commands;

import managers.CollectionManager;
import models.Route;
import utilities.*;

import java.util.Collections;
import java.util.Comparator;

/**
 * print distance descending выводит все значение distance по убыванию
 */
public class PrintDistanceDescending extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public PrintDistanceDescending(Console console, CollectionManager collectionManager) {
        super("print_field_descending_distance", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении посортированные убыванию");
        this.console = console;
        this.collectionManager = collectionManager;
    }
    @Override
    public Execute apply(String[] arguments) {
        if (!arguments[1].isEmpty()) return new Execute(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
        collectionManager.getCollection().sort(Comparator.reverseOrder());
        String s = "";
        for(Route a : collectionManager.getCollection()){
            s += String.valueOf(a.getDistance()) + ", ";
        }
        String result = s.substring(0, s.length() - 2);
        collectionManager.update();
        return new Execute(result);
    }
}
