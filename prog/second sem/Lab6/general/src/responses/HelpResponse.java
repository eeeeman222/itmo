package responses;

import genutilities.Commands;

public class HelpResponse extends Response{

    public final String help;

    public HelpResponse(String help, String error){
        super(Commands.HELP, error);
        this.help = help;
    }

}
