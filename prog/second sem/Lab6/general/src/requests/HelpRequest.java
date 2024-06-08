package requests;

import genutilities.Commands;

public class HelpRequest extends Request {
    public HelpRequest() {
        super(Commands.HELP);
    }
}