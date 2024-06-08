package requests;

import genutilities.Commands;

public class PrintDescendingRequest extends Request{

    public PrintDescendingRequest(){
        super(Commands.PRINT_DESCENDING);
    }
}
