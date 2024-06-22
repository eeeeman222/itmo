package responses;

import genutilities.Commands;

public class RemoveResponse extends Response{

    public RemoveResponse(String error){
        super(Commands.REMOVE_BY_ID, error);
    }
}
