package requests;

import genutilities.Commands;
import models.User;

public class HelpRequest extends Request {

    public HelpRequest(User user) {
        super(Commands.HELP, user);
    }
}