package requests;

import genutilities.Commands;

public class HistoryRequest extends Request {
    public HistoryRequest() {
        super(Commands.HISTORY);
    }
}