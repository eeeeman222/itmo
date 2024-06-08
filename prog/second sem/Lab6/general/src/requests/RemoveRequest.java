package requests;

import genutilities.Commands;

public class RemoveRequest extends Request{

    public final int id;

    public RemoveRequest(int id){
        super(Commands.REMOVE_BY_ID);
        this.id = id;
    }
}
