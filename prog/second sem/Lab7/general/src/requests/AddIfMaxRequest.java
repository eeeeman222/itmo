package requests;

import models.Route;
import genutilities.Commands;
import models.User;

public class AddIfMaxRequest extends Request {
  public final Route route;

  public AddIfMaxRequest(Route route, User user) {
    super(Commands.ADD_IF_MAX, user);
    this.route = route;
  }
}