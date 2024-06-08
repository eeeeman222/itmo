package responses;

import genutilities.Commands;

public class RemoveLowerResponse extends Response{

    public RemoveLowerResponse(String error){
        super(Commands.REMOVE_LOWER, error);
    }
}
