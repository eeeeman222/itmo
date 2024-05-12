package commands;

import managers.CollectionManager;
import utilities.Console;
import managers.ParseManager;
import utilities.Execute;
import utilities.MyConsole;

import java.io.IOException;

/**
 * Записывает коллекции в файл
 */
public class Save extends Command {
    private final Console console;
    private final CollectionManager collectionManager;
    public Save(Console console, CollectionManager collectionManager) {
        super("save", "сохранить коллекцию в файл");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     *
     * @param arguments
     * @return успех или неуспех выполнения
     * @throws IOException
     */
    @Override
    public Execute apply(String[] arguments) throws IOException {
        if (!arguments[1].isEmpty()) return new Execute(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");

        collectionManager.save();
        return new Execute(true, "");
    }
}