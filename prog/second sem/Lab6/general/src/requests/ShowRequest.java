package requests;

import genutilities.Commands;

public class ShowRequest extends Request {
    public ShowRequest() {
        super(Commands.SHOW);
    }
}