package commands;

import myExceptions.WrongDistanceException;
import managers.CollectionManager;
import models.Asker;
import models.Route;
import utilities.Console;
import utilities.Execute;
import utilities.MyConsole;

/**
 * Команда 'add'. Добавляет новый элемент в коллекцию.
 */
public class Add extends Command {
    private final Console console;
    private final CollectionManager collectionManager;


    public Add(Console console, CollectionManager collectionManager) {
        super("add {element}", "добавить новый элемент в коллекцию");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * выполням команду
     * @param arguments
     * @return статус выполнения команды
     */
    public Execute apply(String[] arguments) {
        try {
            if (!arguments[1].isEmpty()) return new Execute(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
            Route d = Asker.askRoute(console, (int)collectionManager.getFreeId());
            if (d != null && d.validate()) {
                collectionManager.add(d);
                return new Execute("Route успешно добавлен!");
            } else return new Execute(false,"Поля Route не валидны! Route не создан!");
        } catch (Asker.AskBreak e) {
            return new Execute(false,"Отмена...");
        }
    }
}
