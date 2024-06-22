package commands;

import models.Route;
import requests.*;
import responses.*;
import servmanagers.CollectionManager;

import java.util.LinkedList;
import java.util.List;

/**
 * команда Cnt_Greater_Distance выводит количество route с distance большей, чем заданная
 */
public class CntGreaterDistance extends Command {
    private final CollectionManager collectionManager;

    private final List<Route> routes;


    public CntGreaterDistance(CollectionManager collectionManager) {
        super("count greater than distance", "вывести количество элементов коллекции с значением поля distance меньшем введенного");
        this.collectionManager = collectionManager;
        routes  =  collectionManager.getCollection();
    }


    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public Response apply(Request request) {
        try {
            var req = (CountGreaterThanDistanceRequest) request;
            long dis = req.dis;
            int cnt = 0;
            for (var a : routes) {
                if (dis < a.getDistance()) {
                    cnt++;
                }
            }
            return new CountGreaterThanDistanceResponse(cnt, null);
        }
        catch (Exception e){
            return new CountGreaterThanDistanceResponse(-1, e.toString());
        }
    }
}
