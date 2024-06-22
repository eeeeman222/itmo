package commands;

import requests.*;
import responses.*;
import servmanagers.*;

/**
 * Команда 'add'. Добавляет новый элемент.
 */
public class Add extends Command{
    private final CollectionManager collectionManager;


    public Add(CollectionManager collectionManager) {
        super("add {element}", "добавить новый элемент в коллекцию");
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     */
    public AddResponse apply(Request request) {
        var req = (AddRequest) request;
        try {
            req.route.setId(collectionManager.getFreeId());
            if (!req.route.validate()) {
                return new AddResponse(-1, "Поля route не валидны! route не добавлен!");
            }
            var newId = collectionManager.add(req.route);
            JDBCManager.addRoute(req.route);

            return new AddResponse(newId, null);
        } catch (Exception e) {
            return new AddResponse(-1, e.toString());
        }
    }
}
