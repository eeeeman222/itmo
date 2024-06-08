package responses;

import models.Route;
import genutilities.Commands;

import java.util.List;

public class ShowResponse extends Response{

    public final String str;

    public ShowResponse(String str, String error){
        super(Commands.SHOW, error);
        this.str = str;
    }
}
