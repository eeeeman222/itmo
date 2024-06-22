package commands;

import managers.*;
import net.UDPClient;
import utilities.*;
import requests.AuthenticateRequest;
import responses.AuthenticateResponse;

import java.io.IOException;
public class Authorizate extends Command {
  private final Console console;
  private final UDPClient client;

  public Authorizate(Console console, UDPClient client) {
    super("auth", "вход в свой аакаунт");
    this.console = console;
    this.client = client;
  }

  /**
   * Выполняет команду
   *
   * @return Успешность выполнения команды.
   */
  @Override
  public boolean apply(String[] arguments) throws Asker.AskBreak, ClassNotFoundException {
    try {
      if (!arguments[1].isEmpty()) ;
      console.println("Заходим | :*");

      var user = Asker.askUser(console);

      var response = (AuthenticateResponse) client.sendAndReceiveCommand(new AuthenticateRequest(user));
      if (response.getError() != null && !response.getError().isEmpty()) {
        console.println(response.getError());
        return false;
      }

      SessionHandler.setCurrentUser(response.user);
      console.println("вы " + response.user.getName() + " с id=" + response.user.getId() + " и вы зашли успешно");
    } catch (IOException e) {
      console.printError(e.toString());
    }
    return true;
  }
}