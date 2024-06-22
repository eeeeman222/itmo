package requests;

import genutilities.Commands;
import models.User;

public class PrintFieldDescendingDistanceRequest extends Request{


    public PrintFieldDescendingDistanceRequest(User user){
        super(Commands.PRINT_FIELD_DESCENDING_DISTANCE, user);
    }
}
