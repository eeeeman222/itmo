package requests;

import models.Route;
import genutilities.Commands;

public class UpdateRequest extends Request {
    public final Route route;

    public final int id;

    public UpdateRequest(Route route, int id) {
        super(Commands.UPDATE);
        this.route = route;
        this.id = id;
    }
}