package responses;
import models.User;
import genutilities.Commands;

public class RegisterResponse extends Response{

    public final User user;

    public RegisterResponse(User user, String error){
        super(Commands.REGISTER, error);
        this.user = user;
    }

}
