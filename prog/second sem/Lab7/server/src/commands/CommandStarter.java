package commands;

import requests.Request;
import responses.*;
import servmanagers.*;

import java.sql.SQLException;

public class CommandStarter {
  private final CommandManager manager;

  public CommandStarter(CommandManager manager) {
    this.manager = manager;
  }

  public Response handle(Request request) throws SQLException {
    var command = manager.getCommands().get(request.getName());
    if (command == null) return new NoCommandResponse(request.getName());
    manager.addToHistory(command.getName());
    return command.apply(request);
  }
}