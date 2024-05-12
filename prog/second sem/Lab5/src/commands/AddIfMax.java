package commands;

import myExceptions.WrongDistanceException;
import managers.CollectionManager;
import models.Asker;
import models.Route;
import utilities.Console;
import utilities.Execute;
import utilities.MyConsole;

import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * Команда 'addIfMax'. Добавляет новый элемент в коллекцию, если у него максимальная distance.
 */
public class AddIfMax extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    private LinkedList<Route> routes;

    public AddIfMax(Console console, CollectionManager collectionManager) {
        super("add_if_max {element}", "добавить новый элемент в коллекцию, если его distance максимальна");
        this.console = console;
        this.collectionManager = collectionManager;
        routes = collectionManager.getCollection();
    }

    /**
     * Выполняем команду
     */
    public Execute apply(String[] arguments) {
        try {
            if (!arguments[1].isEmpty()) return new Execute(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
            Route d = Asker.askRoute(console, (int)collectionManager.getFreeId());
            int maxDistance = 0;
            for (Route a : routes){
                maxDistance = max(maxDistance, a.getDistance());
            }
            if (d != null && d.validate()) {
                if(d.getDistance() > collectionManager.getMaxDistance()){
                    collectionManager.add(d);
                    return new Execute("Route успешно добавлен!");
                }
                else{
                    return new Execute(false, "Данный Route не максимален, поэтому он не добавлен.");
                }
            } else return new Execute(false,"Поля Route не валидны! Route не создан!");
        } catch (Asker.AskBreak e) {
            return new Execute(false,"Отмена...");
        }
    }
}

