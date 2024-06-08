package requests;

import models.Route;
import genutilities.Commands;

public class RemoveLowerRequest extends Request{

    public final Route route;

    public RemoveLowerRequest(Route route){
        super(Commands.REMOVE_LOWER);
        this.route = route;
    }
}
