package commands;

import models.Route;
import requests.*;
import responses.*;
import servmanagers.CollectionManager;

import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * print distance descending выводит все значение distance по убыванию
 */
public class PrintDistanceDescending extends Command {
    private final CollectionManager collectionManager;

    public PrintDistanceDescending(CollectionManager collectionManager) {
        super("print_field_descending_distance", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении посортированные убыванию");
        this.collectionManager = collectionManager;
    }
    @Override
    public Response apply(Request request) {
        try {
            String s = collectionManager.getCollection().stream().sorted(Comparator.comparing(Route::getDistance).reversed()).map(a -> a.getDistance() + ", ").collect(Collectors.joining());


            collectionManager.update();
            return new PrintFieldDescendingDistanceResponse(s, null);
        }
        catch (Exception e){
            return new PrintFieldDescendingDistanceResponse(null, e.toString());
        }
    }
}
