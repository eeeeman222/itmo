package requests;

import models.Route;
import genutilities.Commands;
import models.User;

public class RemoveLowerRequest extends Request{

    public final Route route;

    public RemoveLowerRequest(Route route, User user){
        super(Commands.REMOVE_LOWER, user);
        this.route = route;
    }
}
