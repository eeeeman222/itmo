package commands;

import net.UDPClient;
import requests.HistoryRequest;
import requests.InfoRequest;
import responses.HistoryResponse;
import responses.InfoResponse;
import utilities.Console;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Команда 'info'. Выводит информацию о коллекции.
 */
public class Info extends Command {
    private final Console console;
    private final UDPClient client;

    public Info(Console console, UDPClient client) {
        super("info", "вывести информацию о коллекции");
        this.console = console;
        this.client = client;
    }

    /**
     * Выполняет команду
     *
     * @return Успешность выполнения команды.
     */
    @Override
    public boolean apply(String[] arguments) {
        if (!arguments[1].isEmpty()) {
            console.printError("Неправильное количество аргументов!");
            console.println("Использование: '" + getName() + "'");
            return false;
        }
        try {
            var response = (InfoResponse) client.sendAndReceiveCommand(new InfoRequest());
            if (response.getError() != null && !response.getError().isEmpty()) {
                console.println(response.getError());
                return false;
            }
            console.println(response.info);
            return true;
        } catch(IOException e) {
            console.printError("Ошибка взаимодействия с сервером");
        } catch (ClassNotFoundException e) {
            console.printError("ошибка сериализации");
        }
        return false;
    }
}
