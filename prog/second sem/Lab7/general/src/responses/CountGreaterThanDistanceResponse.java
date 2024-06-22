package responses;

import genutilities.Commands;

public class CountGreaterThanDistanceResponse extends Response{

    public final long cnt;

    public CountGreaterThanDistanceResponse(long cnt, String error){
        super(Commands.COUNT_GREATER_THAN_DISTANCE, error);

        this.cnt = cnt;
    }
}
