package commands;

import models.Route;
import requests.RemoveLowerRequest;
import requests.Request;
import responses.RemoveLowerResponse;
import responses.Response;
import servmanagers.CollectionManager;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * команда remove if min удаляет все объекты коллекции, значение поля distance которых меньше чем у введенного route
 */
public class RemoveLower extends Command {
    private final CollectionManager collectionManager;

    private final LinkedList<Route> routes;

    public RemoveLower(CollectionManager collectionManager) {
        super("remove_lower{element}", "удалить элементы из коллекции, distance которых меньше заданных");
        this.collectionManager = collectionManager;
        this.routes = collectionManager.getCollection();
    }

    /**
     * Выполняет команду
     */
    @Override
    public Response apply(Request request){
        try {
            var req = (RemoveLowerRequest) request;
            var route = req.route;
            List<Long> RemID = routes.stream().filter(a -> route.getDistance() > a.getDistance()).map(Route::getId).toList();
            RemID.forEach(collectionManager::remove);
            collectionManager.update();
            return new RemoveLowerResponse(null);
        }
        catch (Exception e){
            return new RemoveLowerResponse(e.toString());
        }
    }
}
