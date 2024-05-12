package commands;

import managers.CollectionManager;
import utilities.*;
import models.Route;

/**
 * Команда 'clear'. Очищает коллекцию.
 */
public class Clear extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public Clear(Console console, CollectionManager collectionManager) {
        super("clear", "очистить коллекцию");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняем команду
     */
    @Override
    public Execute apply(String[] arguments) {
        if (!arguments[1].isEmpty())
            return new Execute(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");

        while (collectionManager.getCollection().size() > 0) {
            collectionManager.remove(collectionManager.getCollection().getFirst().getId());
        }

        collectionManager.update();
        return new Execute("Коллекция очищена!");
    }
}