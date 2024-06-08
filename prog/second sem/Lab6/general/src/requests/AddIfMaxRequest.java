package requests;

import models.Route;
import genutilities.Commands;

public class AddIfMaxRequest extends Request {
  public final Route route;

  public AddIfMaxRequest(Route route) {
    super(Commands.ADD_IF_MAX);
    this.route = route;
  }
}