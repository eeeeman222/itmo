package responses;

public class NoCommandResponse extends Response{
    public NoCommandResponse(String commmand){
        super(commmand, "команда не обнаружена");
    }
}
