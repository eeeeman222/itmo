package commands;

import utilities.*;
import managers.CollectionManager;

import java.time.LocalDateTime;

/**
 * Команда 'info'. Выводит информацию о коллекции.
 */
public class Info extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public Info(Console console, CollectionManager collectionManager) {
        super("info", "вывести информацию о коллекции");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     *
     * @return Успешность выполнения команды.
     */
    @Override
    public Execute apply(String[] arguments) {
        if (!arguments[1].isEmpty()) {
            return new Execute(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
        }
        LocalDateTime lastInitTime = collectionManager.getLastInitTime();
        String lastInitTimeString = (lastInitTime == null) ? "в данной сессии инициализации еще не происходило" :
                lastInitTime.toLocalDate().toString() + " " + lastInitTime.toLocalTime().toString();

        LocalDateTime lastSaveTime = collectionManager.getLastSaveTime();
        String lastSaveTimeString = (lastSaveTime == null) ? "в данной сессии сохранения еще не происходило" :
                lastSaveTime.toLocalDate().toString() + " " + lastSaveTime.toLocalTime().toString();

        var s = "Сведения о коллекции:\n";
        s += " Тип: " + collectionManager.getCollection().getClass().toString() + "\n";
        s += " Количество элементов: " + collectionManager.getCollection().size() + "\n";
        s += " Дата последнего сохранения: " + lastSaveTimeString + "\n";
        s += " Дата последней инициализации: " + lastInitTimeString;
        return new Execute(s);
    }
}
