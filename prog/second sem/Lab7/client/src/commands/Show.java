package commands;
/**
 * команда show выводит все route
 */

import net.UDPClient;
import requests.RemoveLowerRequest;
import requests.ShowRequest;
import responses.RemoveLowerResponse;
import responses.ShowResponse;
import utilities.Console;
import utilities.SessionHandler;

import java.io.IOException;

public class Show extends Command {
    private final Console console;
    private final UDPClient client;

    public Show(Console console, UDPClient client) {
        super("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        this.console = console;
        this.client = client;
    }

    /**
     *
     * @param arguments
     * @return статус выполнения
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
        try{
            var response = (ShowResponse) client.sendAndReceiveCommand(new ShowRequest(SessionHandler.getCurrentUser()));
            if (response.getError() != null && !response.getError().isEmpty()) {
                console.println(response.getError());
                return false;
            }
            console.println(response.str);
            return true;
        } catch (IOException e) {
            console.printError("Ошибка взаимодействия с сервером");
        } catch (ClassNotFoundException e) {
            console.printError("ошибка сериализации");
        }

        return false;
    }
}
