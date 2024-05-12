package commands;

import managers.CollectionManager;
import managers.CommandManager;
import utilities.Console;
import utilities.Execute;
import utilities.MyConsole;

import java.util.stream.Collectors;

/**
 * команда help выводит доступные для нас команды с описанем
 */
public class Help extends Command{
    private final Console console;
    private final CommandManager commandManager;

    public Help(Console console, CommandManager commandManager) {
        super("help", "вывести в стандартный поток вывода все команды и краткую информацию о них");
        this.console = console;
        this.commandManager = commandManager;
    }
    @Override
    public Execute apply(String[] arguments) {
        if (!arguments[1].isEmpty()) return new Execute(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");

        return new Execute(commandManager.getCommands().values().stream().map(command -> String.format(" %-35s%-1s%n", command.getName(), command.getDescription())).collect(Collectors.joining("\n")));
    }



}
