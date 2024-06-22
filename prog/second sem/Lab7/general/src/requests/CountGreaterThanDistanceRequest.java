package requests;

import genutilities.Commands;
import models.User;

public class CountGreaterThanDistanceRequest extends Request{

    public final int dis;
    public CountGreaterThanDistanceRequest(int dis, User user){
        super(Commands.COUNT_GREATER_THAN_DISTANCE, user);
        this.dis = dis;
    }
}
