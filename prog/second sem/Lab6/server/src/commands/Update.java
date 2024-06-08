package commands;

import models.Route;
import requests.*;
import responses.*;
import servmanagers.CollectionManager;

/**
 * Команда 'update'. Обновляет элемент коллекции по id.
 */
public class Update extends Command {
    private final CollectionManager collectionManager;

    public Update(CollectionManager collectionManager) {
        super("update <ID> {element}", "обновить значение элемента коллекции по ID");
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public Response apply(Request request) {
        try {
            long id = -1;
            var req = (UpdateRequest) request;
            try {
                id = req.id;
            } catch (NumberFormatException e) {
                return new UpdateResponse("ID не распознан");
            }

            var old = collectionManager.byId((int) id);
            if (old == null || !collectionManager.getCollection().contains(old)) {
                return new UpdateResponse( "Не существующий ID");
            }
            var d = req.route;
            if (d != null && d.validate()) {
                collectionManager.updateById(d, (int)old.getId());
                return new UpdateResponse(null);
            } else {
                return new UpdateResponse("Поля не валидны!");
            }
        }
        catch (Exception e){
            return new UpdateResponse(e.toString());
        }
    }
}
