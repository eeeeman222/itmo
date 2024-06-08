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
public class CntGreaterDistance extends Command {
    private final Console console;

    private final UDPClient client;

    public CntGreaterDistance(Console console, UDPClient client) {
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
            if (arguments[1].isEmpty()) {
                console.println("Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
            }
            var k = Integer.parseInt(arguments[1]);
            var response = (CountGreaterThanDistanceResponse) client.sendAndReceiveCommand(new CountGreaterThanDistanceRequest(k));
            if (response.getError() != null && !response.getError().isEmpty()) {
                console.println(response.getError());
                return false;
            }
            long y = response.cnt;

            console.println("получен ответ - " + y);
            return true;
        } catch (IOException e) {
            console.printError("Ошибка взаимодействия с сервером");
            return false;
        }
    }
}