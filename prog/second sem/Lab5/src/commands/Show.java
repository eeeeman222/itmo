package commands;
/**
 * команда show выводит все route
 */
import managers.CollectionManager;
import utilities.*;
public class Show extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public Show(Console console, CollectionManager collectionManager) {
        super("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     *
     * @param arguments
     * @return статус выполнения
     */
    @Override
    public Execute apply(String[] arguments) {
        if (!arguments[1].isEmpty()) return new Execute(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");

        return new Execute(collectionManager.toString());
    }
}
