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
public class AddIfMax extends Command {
    private final Console console;

    private final UDPClient client;

    public AddIfMax(Console console, UDPClient client) {
        super("add if max {element}", "добавить элемент, если distance максимальна");
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

            console.println("* Создание нового продукта:");


            var newRoute = Asker.askRoute(console, 0, SessionHandler.getCurrentUser().getId());
            var response = (AddIfMaxResponse) client.sendAndReceiveCommand(new AddIfMaxRequest(newRoute, SessionHandler.getCurrentUser()));
            if (response.getError() != null && !response.getError().isEmpty()) {
                console.println(response.getError());
                return false;
            }

            console.println("Новый продукт с id = " + response.newId + " успешно добавлен!");
            return true;
        } catch (IOException e) {
            console.printError("Ошибка взаимодействия с сервером");
            return false;
        } catch (Asker.AskBreak e) {
            throw new RuntimeException(e);
        }
    }
}