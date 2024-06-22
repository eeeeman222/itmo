package requests;

import genutilities.Commands;
import models.User;

public class ShowRequest extends Request {

    public ShowRequest(User user) {
        super(Commands.SHOW, user);
    }
}