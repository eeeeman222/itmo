package commands;

import managers.CollectionManager;
import models.Asker;
import utilities.*;

/**
 * Команда 'update'. Обновляет элемент коллекции по id.
 */
public class Update extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public Update(Console console, CollectionManager collectionManager) {
        super("update <ID> {element}", "обновить значение элемента коллекции по ID");
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
        try {
            if (arguments[1].isEmpty())
                return new Execute(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
            long id = -1;
            try {
                id = Long.parseLong(arguments[1].trim());
            } catch (NumberFormatException e) {
                return new Execute(false, "ID не распознан");
            }

            var old = collectionManager.byId((int) id);
            if (old == null || !collectionManager.getCollection().contains(old)) {
                return new Execute(false, "Не существующий ID");
            }

            console.println("* Создание Route:");
            var d = Asker.askRoute(console, old.getId());
            if (d != null && d.validate()) {
                collectionManager.updateById(d, (int)old.getId());
                return new Execute("Обновлено!");
            } else {
                return new Execute(false, "Поля не валидны!");
            }
        } catch (Asker.AskBreak e) {
            return new Execute(false, "Поля не валидны!");
        }
    }
}
