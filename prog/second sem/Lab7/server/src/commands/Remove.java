package commands;

import requests.*;
import responses.*;
import servmanagers.CollectionManager;
import servmanagers.JDBCManager;

/**
 * Команда 'remove_by_id'. Удаляет элемент из коллекции.
 */
public class Remove extends Command {
    private final CollectionManager collectionManager;

    public Remove(CollectionManager collectionManager) {
        super("remove_by_id <ID>", "удалить элемент из коллекции по ID");
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public Response apply(Request request) {
        int id = -1;
        var req = (RemoveRequest) request;
        try { id = req.id; } catch (NumberFormatException e) { return new RemoveResponse("ID не получен"); }

        if (collectionManager.byId(id) == null || !collectionManager.getCollection().contains(collectionManager.byId(id)))
            return new RemoveResponse("Не существующий ID");
        if(req.getUser().getId() == collectionManager.byId(id).getCreatorId()) {
            collectionManager.remove(id);
            collectionManager.update();
            JDBCManager.deleteRouteById(id);
        }
        else{
            return new RemoveResponse("это чужой route!");
        }
        return new RemoveResponse(null);
    }
}