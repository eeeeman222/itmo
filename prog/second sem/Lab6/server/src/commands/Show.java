package commands;

import models.Route;
import requests.*;
import responses.*;
import servmanagers.CollectionManager;
public class Show extends Command {
    private final CollectionManager collectionManager;

    public Show(CollectionManager collectionManager) {
        super("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        this.collectionManager = collectionManager;
    }

    /**
     *
     * @return статус выполнения
     */
    @Override
    public Response apply(Request request) {

        return new ShowResponse(collectionManager.toString(), null);
    }
}
