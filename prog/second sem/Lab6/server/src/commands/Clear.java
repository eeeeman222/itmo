package commands;

import models.Route;
import requests.*;
import responses.*;
import servmanagers.CollectionManager;

import java.util.LinkedList;
import java.util.stream.Collectors;

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
            collectionManager.getCollection().clear();
            return new ClearResponse("коллекция очищена!");
        }
        catch(Exception e){
            return new ClearResponse(e.toString());
        }
    }
}