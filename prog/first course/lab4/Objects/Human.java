package Objects;

import Interfaces.InProperties;
import Interfaces.InTime;
import enums.Action;

public class Human extends Alive implements InProperties, InTime {
    static private String name;
    public static String name2 = name;
    @Override
    public String toString() {
        return name;
    }
    public Human(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        final int PRIME = 40;
        int result = PRIME;
        result = PRIME * name.hashCode();
        return result;
    }
    @Override
    public void prop(String proper){
        name2 = name + " " + proper;
    }
    @Override
    public void activity(Action act){
        if (act == Action.NOSURPRISE) {
            System.out.println(name2 + " не удивилась.");
        }
        if(act == Action.JUMP){
            System.out.println(name2 + "подскочила.");
        }

    }
    public static void interaction(Object b, Action act) {
        System.out.print(name2);
        if(act == Action.WEAVE){
            System.out.print(" решила сплести ");
        }
        if(act == Action.COLLECT){
            System.out.print(" для этого надо собрать ");
        }
        System.out.println(b + ".");
    }

    @Override
    public void txtact(Action act, String str){
        if(act == Action.THINK){
            System.out.println(name2 + "подумала, что " + str);
        }
    }
    public void time(String s){
        System.out.print(s + " ");
    }




}