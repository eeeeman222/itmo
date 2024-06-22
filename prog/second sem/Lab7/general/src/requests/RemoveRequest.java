package requests;

import genutilities.Commands;
import models.User;

public class RemoveRequest extends Request{
    public final int id;

    public RemoveRequest(int id, User user){
        super(Commands.REMOVE_BY_ID, user);
        this.id = id;
    }
}
