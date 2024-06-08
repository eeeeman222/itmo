package responses;

import genutilities.Commands;

public class ClearResponse extends Response{

    public ClearResponse(String error){
        super(Commands.CLEAR, error);
    }
}
