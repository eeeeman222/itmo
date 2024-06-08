package commands;

import models.Route;
import requests.*;
import responses.*;
import servmanagers.CollectionManager;

import java.util.Comparator;

/**
 * print descending выводит объекты по убыванию distance
 */
public class PrintDescending extends Command {
    private final CollectionManager collectionManager;

    public PrintDescending(CollectionManager collectionManager) {
        super("print_descending", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении посортированные убыванию");
        this.collectionManager = collectionManager;
    }

    public Response apply(Request request) {
        try {
            collectionManager.getCollection().sort(Comparator.reverseOrder());
            String s = collectionManager.toString();
            collectionManager.update();
            return new PrintDescendingResponse(s, null);
        }
        catch (Exception e){
            return new PrintDescendingResponse(null, e.toString());
        }
    }
}
