package responses;

import genutilities.Commands;
import models.User;

public class AuthenticateResponse extends Response {
  public final User user;

  public AuthenticateResponse(User user, String error) {
    super(Commands.AUTHENTICATE, error);
    this.user = user;
  }
}