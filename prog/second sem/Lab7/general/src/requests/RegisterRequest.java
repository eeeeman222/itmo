package requests;

import models.User;
import genutilities.Commands;

public class RegisterRequest extends Request {
    public RegisterRequest(User user) {
        super(Commands.REGISTER, user);
    }
}