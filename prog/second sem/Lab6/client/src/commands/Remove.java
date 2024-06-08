package commands;

import net.UDPClient;
import requests.PrintFieldDescendingDistanceRequest;
import requests.RemoveRequest;
import responses.PrintFieldDescendingDistanceResponse;
import responses.RemoveResponse;
import utilities.Console;

import java.io.IOException;

/**
 * Команда 'remove_by_id'. Удаляет элемент из коллекции.
 */
public class Remove extends Command {
    private final Console console;
    private final UDPClient client;

    public Remove(Console console, UDPClient client) {
        super("remove <ID>", "удалить элемент из коллекции по ID");
        this.console = console;
        this.client = client;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public boolean apply(String[] arguments) {
        if (arguments[1].isEmpty()){
            console.printError("Неправильное количество аргументов!");
            console.println("Использование: '" + getName() + "'");
            return false;
        }
        int id;
        try { id = Integer.parseInt(arguments[1].trim()); } catch (NumberFormatException e) {
            console.println("неверный формат id");
            return false;
        }
        try {
            var response = (RemoveResponse) client.sendAndReceiveCommand(new RemoveRequest(id));
            if (response.getError() != null && !response.getError().isEmpty()) {
                console.printError(response.getError());
                return false;
            }
            console.println("удалено!");
            return true;
        } catch (IOException e) {
            console.printError("Ошибка взаимодействия с сервером");
        } catch (ClassNotFoundException e) {
            console.printError("ошибка сериализации");
        }
        return false;
    }
}