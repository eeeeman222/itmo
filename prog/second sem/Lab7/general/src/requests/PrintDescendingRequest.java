package requests;

import genutilities.Commands;
import models.User;

public class PrintDescendingRequest extends Request{

    public PrintDescendingRequest(User user){
        super(Commands.PRINT_DESCENDING, user);
    }
}
