package Objects;

import MyExceptions.SpacePocketException;

public class Pocket {
    String thing;
    public void setThing(String thing)
    {
        this.thing = thing;
    }

    public String getThing(){
        return thing;
    }

    public void isThing() throws SpacePocketException {
        if(thing.equals("часы")){
            System.out.println("Кролик достал из кармана часы.");
        }
        else
        {
            throw new SpacePocketException(thing);
        }
    }
}
