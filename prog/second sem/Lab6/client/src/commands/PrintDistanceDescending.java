package commands;

import net.UDPClient;
import requests.InfoRequest;
import requests.PrintDescendingRequest;
import requests.PrintFieldDescendingDistanceRequest;
import responses.InfoResponse;
import responses.PrintDescendingResponse;
import responses.PrintFieldDescendingDistanceResponse;
import utilities.Console;

import java.io.IOException;
import java.util.Comparator;

/**
 * print descending выводит объекты по убыванию distance
 */
public class PrintDistanceDescending extends Command {
    private final Console console;
    private final UDPClient client;

    public PrintDistanceDescending(Console console, UDPClient client) {
        super("print_distance_descending", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении посортированные убыванию");
        this.console = console;
        this.client = client;
    }
    @Override
    public boolean apply(String[] arguments) {
        if (!arguments[1].isEmpty()){
            console.printError("Неправильное количество аргументов!");
            console.println("Использование: '" + getName() + "'");
            return false;
        }
        try{
            var response = (PrintFieldDescendingDistanceResponse) client.sendAndReceiveCommand(new PrintFieldDescendingDistanceRequest());
            if (response.getError() != null && !response.getError().isEmpty()) {
                console.println(response.getError());
                return false;
            }
            console.println("получен ответ - " + response.str);
            return true;
        } catch (IOException e) {
            console.printError("Ошибка взаимодействия с сервером");
        } catch (ClassNotFoundException e) {
            console.printError("ошибка сериализации");
        }
        return false;
    }
}
