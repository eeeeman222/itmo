package commands;

import managers.Asker;
import requests.*;
import responses.*;
import utilities.*;
import net.*;

import java.io.IOException;

/**
 * Команда 'add'. Добавляет новый элемент в коллекцию.
 */
public class Clear extends Command {
    private final Console console;

    private final UDPClient client;

    public Clear(Console console, UDPClient client) {
        super("add {element}", "добавить элемент");
        this.console = console;
        this.client = client;
    }

    /**
     * Выполняет команду
     *
     * @return Успешность выполнения команды.
     */
    @Override
    public boolean apply(String[] arguments) throws ClassNotFoundException {
        try {
            if(SessionHandler.getCurrentUser() == null){
                console.printError("зарегистрируйтесь или войдите, прежде чем манипулировать данными!");
                return false;
            }
            if (!arguments[1].isEmpty()) {
                console.println("Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
            }
            var response = (ClearResponse) client.sendAndReceiveCommand(new ClearRequest(SessionHandler.getCurrentUser()));
            if (response.getError() != null && !response.getError().isEmpty()) {
                console.println(response.getError());
                return false;
            }

            console.println("коллекция очищена!");
            return true;
        } catch (IOException e) {
            console.printError("Ошибка взаимодействия с сервером");
            return false;
        }
    }
}