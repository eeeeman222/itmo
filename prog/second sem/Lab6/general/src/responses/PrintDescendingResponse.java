package responses;

import models.Route;
import genutilities.Commands;

import java.util.List;

public class PrintDescendingResponse extends Response{

    public final String str;

    public PrintDescendingResponse(String str, String error){
        super(Commands.PRINT_DESCENDING, error);
        this.str = str;
    }
}
