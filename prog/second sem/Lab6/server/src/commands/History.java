package commands;

import models.Route;
import requests.*;
import responses.*;
import servmanagers.CollectionManager;
import servmanagers.CommandManager;

import java.util.stream.Collectors;

/**
 * команда history выводит историю корректно обработанных команд.
 */
public class History extends Command {

    private final CommandManager commandManager;

    public History(CommandManager commandManager) {
        super("history", "показать историю команд(последние 6)");
        this.commandManager = commandManager;
    }
    @Override
    public Response apply(Request request) {
        try {
            return new HistoryResponse(commandManager.getCommandHistory().stream().map(command -> " " + command).collect(Collectors.joining("\n")), null);
        }
        catch (Exception e){
            return new HistoryResponse(null, e.toString());
        }
    }
}
