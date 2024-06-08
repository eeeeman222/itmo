package commands;

import models.Route;
import requests.*;
import responses.*;
import servmanagers.CollectionManager;

import java.time.LocalDateTime;

/**
 * Команда 'info'. Выводит информацию о коллекции.
 */
public class Info extends Command {
    private final CollectionManager collectionManager;

    public Info(CollectionManager collectionManager) {
        super("info", "вывести информацию о коллекции");
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     */
    @Override
    public Response apply(Request request) {
        try {
            LocalDateTime lastInitTime = collectionManager.getLastInitTime();
            String lastInitTimeString = (lastInitTime == null) ? "в данной сессии инициализации еще не происходило" :
                    lastInitTime.toLocalDate().toString() + " " + lastInitTime.toLocalTime().toString();

            LocalDateTime lastSaveTime = collectionManager.getLastSaveTime();
            String lastSaveTimeString = (lastSaveTime == null) ? "в данной сессии сохранения еще не происходило" :
                    lastSaveTime.toLocalDate().toString() + " " + lastSaveTime.toLocalTime().toString();

            var s = "Сведения о коллекции:\n";
            s += " Тип: " + collectionManager.getCollection().getClass().toString() + "\n";
            s += " Количество элементов: " + collectionManager.getCollection().size() + "\n";
            s += " Дата последнего сохранения: " + lastSaveTimeString + "\n";
            s += " Дата последней инициализации: " + lastInitTimeString;
            return new InfoResponse(s, null);
        }
        catch (Exception e){
            return new InfoResponse(null, e.toString());
        }
    }
}
