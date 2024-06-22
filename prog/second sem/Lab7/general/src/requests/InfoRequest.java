package requests;

import genutilities.Commands;
import models.User;

public class InfoRequest extends Request{


    public InfoRequest(User user){
        super(Commands.INFO, user);
    }
}
