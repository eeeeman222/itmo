package commands;

import managers.Asker;
import models.Route;
import net.UDPClient;
import requests.RemoveLowerRequest;
import requests.RemoveRequest;
import responses.RemoveLowerResponse;
import responses.RemoveResponse;
import utilities.Console;
import utilities.SessionHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * команда remove if min удаляет все объекты коллекции, значение поля distance которых меньше чем у введенного route
 */
public class RemoveIfMin extends Command {
    private final Console console;

    private final UDPClient client;

    public RemoveIfMin(Console console, UDPClient client) {
        super("remove_lower{element}", "удалить элементы из коллекции, distance которых меньше заданных");
        this.console = console;
        this.client = client;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public boolean apply(String[] arguments) {
        if(SessionHandler.getCurrentUser() == null){
            console.printError("зарегистрируйтесь или войдите, прежде выполнять команды.\n register or auth");
            return false;
        }
        if (!arguments[1].isEmpty()) {
            console.printError("Неправильное количество аргументов!");
            console.println("Использование: '" + getName() + "'");
            return false;
        }
        try {
            var newRoute = Asker.askRoute(console, 0, SessionHandler.getCurrentUser().getId());
            var response = (RemoveLowerResponse) client.sendAndReceiveCommand(new RemoveLowerRequest(newRoute, SessionHandler.getCurrentUser()));
            if (response.getError() != null && !response.getError().isEmpty()) {
                console.println(response.getError());
                return false;
            }
            console.println("удаление прошло успешно!");
            return true;
        } catch (IOException e) {
            console.printError("Ошибка взаимодействия с сервером");
        } catch (ClassNotFoundException e) {
            console.printError("ошибка сериализации");
        } catch (Asker.AskBreak e) {
            console.printError("ошибка при вводе элемента");
        }
        return false;
    }
    }
