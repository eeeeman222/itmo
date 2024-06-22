package commands;

import requests.*;
import responses.*;
import servmanagers.CollectionManager;
import servmanagers.JDBCManager;

import static java.lang.Math.max;

/**
 * Команда 'addIfMax'. Добавляет новый элемент в коллекцию, если у него максимальная distance.
 */
public class AddIfMax extends Command {
    private final CollectionManager collectionManager;


    public AddIfMax(CollectionManager collectionManager) {
        super("add_if_max {element}", "добавить новый элемент в коллекцию, если его distance максимальна");
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняем команду
     */
    public AddIfMaxResponse apply(Request request) {
        var req = (AddIfMaxRequest) request;
        try {
            req.route.setId(collectionManager.getFreeId());
            if (!req.route.validate()) {
                return new AddIfMaxResponse(false, -1, "Поля route не валидны! route не добавлен!");
            }
            long b = collectionManager.getMaxDistance();
            if(req.route.getDistance() > b) {
                var newId = collectionManager.add(req.route);
                JDBCManager.addRoute(req.route);
                return new AddIfMaxResponse(true, newId, null);
            }
            else{
                return new AddIfMaxResponse(false, -1, "distance не максимальна, route не добавлен");
            }
        } catch (Exception e) {
            return new AddIfMaxResponse(false, -1, e.toString());
        }
    }
}
