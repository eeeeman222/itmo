package requests;

import genutilities.Commands;
import models.User;

public class AuthenticateRequest extends Request {
  public AuthenticateRequest(User user) {
    super(Commands.AUTHENTICATE, user);
  }

}