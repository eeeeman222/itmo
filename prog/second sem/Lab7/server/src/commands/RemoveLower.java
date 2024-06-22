package commands;

import models.Route;
import requests.RemoveLowerRequest;
import requests.Request;
import responses.RemoveLowerResponse;
import responses.Response;
import servmanagers.CollectionManager;
import servmanagers.JDBCManager;

import java.util.List;

/**
 * команда remove if min удаляет все объекты коллекции, значение поля distance которых меньше чем у введенного route
 */
public class RemoveLower extends Command {
    private final CollectionManager collectionManager;

    private final List<Route> routes;

    public RemoveLower(CollectionManager collectionManager) {
        super("remove_lower{element}", "удалить элементы из коллекции, distance которых меньше заданных");
        this.collectionManager = collectionManager;
        this.routes =  collectionManager.getCollection();
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
            for(long a : RemID){
                if(req.getUser().getId() == collectionManager.byId((int)a).getCreatorId()) {
                    collectionManager.remove(a);
                    collectionManager.update();
                    JDBCManager.deleteRouteById(a);
                }
            }
            collectionManager.update();
            return new RemoveLowerResponse("были удалены только routes, созданные вами.");
        }
        catch (Exception e){
            return new RemoveLowerResponse(e.toString());
        }
    }
}
