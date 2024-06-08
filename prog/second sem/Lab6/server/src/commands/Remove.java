package commands;

import models.Route;
import requests.*;
import responses.*;
import servmanagers.CollectionManager;

/**
 * Команда 'remove_by_id'. Удаляет элемент из коллекции.
 */
public class Remove extends Command {
    private final CollectionManager collectionManager;

    public Remove(CollectionManager collectionManager) {
        super("remove <ID>", "удалить элемент из коллекции по ID");
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
        collectionManager.remove(id);
        collectionManager.update();
        return new RemoveResponse(null);
    }
}