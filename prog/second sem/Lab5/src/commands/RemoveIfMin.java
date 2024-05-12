package commands;

import managers.CollectionManager;
import models.Asker;
import models.Route;
import utilities.Console;
import utilities.Execute;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * команда remove if min удаляет все объекты коллекции, значение поля distance которых меньше чем у введенного route
 */
public class RemoveIfMin extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    private final LinkedList<Route> routes;

    public RemoveIfMin(Console console, CollectionManager collectionManager) {
        super("remove_lower{element}", "удалить элементы из коллекции, distance которых меньше заданных");
        this.console = console;
        this.collectionManager = collectionManager;
        this.routes = collectionManager.getCollection();
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public Execute apply(String[] arguments) throws Asker.AskBreak {
        List<Long> RemID = new ArrayList<>();
        if (!arguments[1].isEmpty()) return new Execute(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
        Route d = Asker.askRoute(console, (int)collectionManager.getFreeId());
        for(Route a : routes){
            if (a.getDistance() < d.getDistance()){
                RemID.add(a.getId());
            }
        }
        for(Long id : RemID){
            collectionManager.remove(id);
        }
        collectionManager.update();
        return new Execute("удаление прошло успешно");
    }
}
