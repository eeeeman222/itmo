package commands;

import models.Route;
import requests.*;
import responses.*;
import servmanagers.CollectionManager;
import servmanagers.JDBCManager;

import java.util.List;

/**
 * Команда 'clear'. Очищает коллекцию.
 */
public class Clear extends Command {
    private final CollectionManager collectionManager;

    public Clear(CollectionManager collectionManager) {
        super("clear", "очистить коллекцию");
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняем команду
     */
    @Override
    public Response apply(Request request) {

        try {
            collectionManager.getCollection().removeIf(route -> route.getCreatorId() == request.getUser().getId());
            return new ClearResponse("коллекция очищена! удалены объекты пользователя с id = ");
        }
        catch(Exception e){
            return new ClearResponse(e.toString());
        }
    }
}