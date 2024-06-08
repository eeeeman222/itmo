package responses;

import genutilities.Commands;

import java.util.List;

public class PrintFieldDescendingDistanceResponse extends Response{

    public final String str;

    public PrintFieldDescendingDistanceResponse(String str, String error){
        super(Commands.PRINT_FIELD_DESCENDING_DISTANCE, error);
        this.str = str;
    }
}
