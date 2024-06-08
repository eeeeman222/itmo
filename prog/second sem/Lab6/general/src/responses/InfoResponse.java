package responses;

import genutilities.Commands;

public class InfoResponse extends Response{

    public final String info;

    public InfoResponse(String info, String error){
        super(Commands.INFO, error);
        this.info = info;
    }
}
