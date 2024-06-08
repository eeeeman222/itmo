package requests;

import models.Route;
import genutilities.Commands;

public class AddRequest extends Request {
    public final Route route;

    public AddRequest(Route route) {
        super(Commands.ADD);
        this.route = route;
    }
}