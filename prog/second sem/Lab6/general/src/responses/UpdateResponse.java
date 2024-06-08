package responses;

import genutilities.Commands;

public class UpdateResponse extends Response{

    public UpdateResponse(String error){
        super(Commands.UPDATE, error);
    }
}
