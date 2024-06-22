package requests;

import genutilities.Commands;
import models.User;

public class ClearRequest extends Request {

    public ClearRequest(User user) {
        super(Commands.CLEAR, user);
    }
}