package commands;

import managers.CollectionManager;
import utilities.*;

import java.util.Collections;
import java.util.Comparator;

/**
 * print descending выводит объекты по убыванию distance
 */
public class PrintDescending extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public PrintDescending(Console console, CollectionManager collectionManager) {
        super("print_descending", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении посортированные убыванию");
        this.console = console;
        this.collectionManager = collectionManager;
    }
    @Override
    public Execute apply(String[] arguments) {
        if (!arguments[1].isEmpty()) return new Execute(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
        collectionManager.getCollection().sort(Comparator.reverseOrder());
        String s = collectionManager.toString();
        collectionManager.update();
        return new Execute(s);
    }
}
