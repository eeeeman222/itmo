package commands;

import managers.CollectionManager;
import utilities.*;
import models.*;

/**
 * Команда 'remove_by_id'. Удаляет элемент из коллекции.
 */
public class Remove extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public Remove(Console console, CollectionManager collectionManager) {
        super("remove <ID>", "удалить элемент из коллекции по ID");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public Execute apply(String[] arguments) {
        if (arguments[1].isEmpty()) return new Execute(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
        int id = -1;
        try { id = Integer.parseInt(arguments[1].trim()); } catch (NumberFormatException e) { return new Execute(false, "ID не получен"); }

        if (collectionManager.byId(id) == null || !collectionManager.getCollection().contains(collectionManager.byId(id)))
            return new Execute(false, "Не существующий ID");
        collectionManager.remove(id);
        collectionManager.update();
        return new Execute("удаление прошло успешно");
    }
}