package commands;

import managers.Asker;
import net.UDPClient;
import requests.RemoveRequest;
import requests.UpdateRequest;
import responses.RemoveResponse;
import responses.UpdateResponse;
import utilities.Console;
import utilities.SessionHandler;

import java.io.IOException;

/**
 * Команда 'update'. Обновляет элемент коллекции по id.
 */
public class Update extends Command {
    private final Console console;
    private final UDPClient client;

    public Update(Console console, UDPClient client) {
        super("update <ID> {element}", "обновить значение элемента коллекции по ID");
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
            if(SessionHandler.getCurrentUser() == null){
                console.printError("зарегистрируйтесь или войдите, прежде выполнять команды.\n register or auth");
                return false;
            }
            if (arguments[1].isEmpty()) {
                console.printError("Неправильное количество аргументов!");
                console.println("Использование: '" + getName() + "'");
                return false;
            }
            int id;
            try { id = Integer.parseInt(arguments[1].trim()); } catch (NumberFormatException e) {
                console.printError("неверный формат id");
                return false;
            }
            try {
                var newRoute = Asker.askRoute(console, id, SessionHandler.getCurrentUser().getId());
                var response = (UpdateResponse) client.sendAndReceiveCommand(new UpdateRequest(newRoute, id, SessionHandler.getCurrentUser()));
                if (response.getError() != null && !response.getError().isEmpty()) {
                    console.printError(response.getError());
                    return false;
                }
                console.println("обновлено!");
                return true;
            } catch (IOException e) {
                console.printError("Ошибка взаимодействия с сервером");
            } catch (ClassNotFoundException e) {
                console.printError("ошибка сериализации");
            } catch (Asker.AskBreak e) {
                throw new RuntimeException(e);
            }
        return false;
        }
    }
