package commands;

import managers.CollectionManager;
import managers.ParseManager;
import models.Route;
import utilities.*;

import java.util.LinkedList;

/**
 * exit - команда для выхода
 */
public class Exit extends Command {
    private final Console console;

    public Exit(Console console) {
        super("exit", "завершить программу (без сохранения в файл)");
        this.console = console;
    }
    @Override
    public Execute apply(String[] arguments) {
        if (!arguments[1].isEmpty()) return new Execute(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");

        return new Execute("exit"); //"Завершение выполнения...");
    }
}