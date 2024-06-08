package responses;

import genutilities.Commands;

public class HistoryResponse extends Response{

    public final String history;

    public HistoryResponse(String history, String error){
        super(Commands.HISTORY, error);
        this.history = history;
    }
}
