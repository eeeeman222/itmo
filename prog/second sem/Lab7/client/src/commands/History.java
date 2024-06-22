package commands;

import managers.Asker;
import requests.*;
import responses.*;
import utilities.*;
import net.*;

import java.io.IOException;

public class History extends Command {
    private final Console console;
    private final UDPClient client;

    public History(Console console, UDPClient client) {
        super("help", "Выводит справку по доступным командам");
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
            var response = (HistoryResponse) client.sendAndReceiveCommand(new HistoryRequest(SessionHandler.getCurrentUser()));
            if (response.getError() != null && !response.getError().isEmpty()) {
                console.println(response.getError());
                return false;
            }
            console.println(response.history);
            return true;
        } catch(IOException e) {
            console.printError("Ошибка взаимодействия с сервером");
        } catch (ClassNotFoundException e) {
            console.printError("ошибка сериализации");
        }
        return false;
    }
}