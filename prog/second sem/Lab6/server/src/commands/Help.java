package commands;

import models.Route;
import requests.*;
import responses.*;
import servmanagers.CollectionManager;
import servmanagers.CommandManager;

import java.util.stream.Collectors;

/**
 * команда help выводит доступные для нас команды с описанем
 */
public class Help extends Command{
    private final CommandManager commandManager;

    public Help(CommandManager commandManager) {
        super("help", "вывести в стандартный поток вывода все команды и краткую информацию о них");
        this.commandManager = commandManager;
    }
    @Override
    public Response apply(Request request) {
        try {
            return new HelpResponse(commandManager.getCommands().values().stream().map(command -> String.format(" %-35s%-1s%n", command.getName(), command.getDescription())).collect(Collectors.joining("\n")), null);
        }
        catch (Exception e){
            return new HelpResponse(null, e.toString());
        }
    }



}
