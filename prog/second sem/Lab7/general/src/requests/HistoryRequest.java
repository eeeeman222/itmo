package requests;

import genutilities.Commands;
import models.User;

public class HistoryRequest extends Request {

    public HistoryRequest(User user) {
        super(Commands.HISTORY, user);
    }
}