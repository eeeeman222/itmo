package requests;

import genutilities.Commands;

public class ClearRequest extends Request {
    public ClearRequest() {
        super(Commands.CLEAR);
    }
}