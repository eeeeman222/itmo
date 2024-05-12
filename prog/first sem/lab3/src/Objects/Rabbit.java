package Objects;

import Interfaces.Properties;
import enums.Action;

public class Rabbit extends Alive implements Properties {

    static private String name;

    @Override
    public String toString() {
        return name;
    }
    public Rabbit(String name) {
        this.name = name;
    }
    String name2 = name;

    @Override
    public void prop(String proper){
        name2 = name + " " + proper;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = PRIME;
        result = PRIME * name.hashCode() + result;
        return result;
    }


    public void activity(Action act){
        if (act == Action.GALLOP) {
            System.out.println(name2 + " вдруг прискакал.");
        }
    }

    public void txtact(Action act, String s){
        if(act == Action.SAY) {
            System.out.println(name2 + " сказал: \"" + s + '"');
        }
    }
    public void time(String s){
        System.out.print(s + " ");
    }
}
