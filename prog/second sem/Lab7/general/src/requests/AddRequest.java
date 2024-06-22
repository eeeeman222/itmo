package requests;

import models.Route;
import genutilities.Commands;
import models.User;

public class AddRequest extends Request {
    public final Route route;

    public AddRequest(Route route, User user) {
        super(Commands.ADD, user);
        this.route = route;
    }
}