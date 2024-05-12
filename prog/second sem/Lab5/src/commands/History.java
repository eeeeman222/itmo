package commands;

import managers.CollectionManager;
import managers.CommandManager;
import managers.ParseManager;
import models.Route;
import utilities.*;

import java.util.LinkedList;
import java.util.stream.Collectors;

/**
 * команда history выводит историю корректно обработанных команд.
 */
public class History extends Command {
    private final Console console;

    private final CommandManager commandManager;

    public History(Console console, CommandManager commandManager) {
        super("history", "показать историю команд(последние 6)");
        this.console = console;
        this.commandManager = commandManager;
    }
    @Override
    public Execute apply(String[] arguments) {
        if (!arguments[1].isEmpty()) return new Execute(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");

        return new Execute(commandManager.getCommandHistory().stream().map(command -> " " + command).collect(Collectors.joining("\n")));
    }
}
