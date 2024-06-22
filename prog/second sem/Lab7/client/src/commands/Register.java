package commands;

import models.*;
import net.UDPClient;
import requests.*;
import responses.*;
import utilities.*;
import genutilities.*;
import managers.*;

import java.io.IOException;

public class Register extends Command {
  private final Console console;
  private final UDPClient client;

  public Register(Console console, UDPClient client) {
    super("register", "регистрация нового пользователя");
    this.console = console;
    this.client = client;
  }

  @Override
  public boolean apply(String[] arguments) throws Asker.AskBreak, ClassNotFoundException, IOException {
    try {
      if (!arguments[1].isEmpty()){
        console.println("Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
      }
      console.println("Создание пользователя:");

      var user = Asker.askUser(console);

      var response = (RegisterResponse) client.sendAndReceiveCommand(new RegisterRequest(user));

      if (response.getError() != null && !response.getError().isEmpty()) {
        console.println(response.getError());
        return false;
      }

      SessionHandler.setCurrentUser(response.user);
      console.println("Пользователь " + response.user.getName() +
        " с id=" + response.user.getId() + " успешно создан!");
      return true;
    }
    catch (Asker.AskBreak e) {
      throw new RuntimeException(e);
    }

  }
}