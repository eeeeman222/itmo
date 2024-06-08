package requests;

import genutilities.Commands;

public class CountGreaterThanDistanceRequest extends Request{

    public final int dis;

    public CountGreaterThanDistanceRequest(int dis){
        super(Commands.COUNT_GREATER_THAN_DISTANCE);
        this.dis = dis;
    }
}
