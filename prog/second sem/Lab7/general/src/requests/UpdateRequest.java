package requests;

import models.Route;
import genutilities.Commands;
import models.User;

public class UpdateRequest extends Request {

    public final Route route;

    public final int id;

    public UpdateRequest(Route route, int id, User user) {
        super(Commands.UPDATE, user);
        this.route = route;
        this.id = id;
    }
}