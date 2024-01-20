package Objects;
import Interfaces.InProperties;
import Interfaces.InTime;
import MyExceptions.RabbitEyesException;
import enums.Action;

public class Rabbit extends Alive implements InProperties, InTime {

    static private String name;

    @Override
    public String toString() {
        return name;
    }
    public Rabbit(String name) {
        this.name = name;
    }



    String name2 = name;

    public static String getName() {
        return name;
    }

    static String skin = "белый ";

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
            System.out.println(name + " вдруг прискакал.");
        }
        if (act == Action.RUN) {
            System.out.println(name2 + " побежал.");
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
    public String action(String action)
    {
        return ("%s %s".formatted(name2,action));
    }


    public class Eyes {
        String color;

        public void getColor(String color_eyes){
            this.color = color_eyes;
        }

        public void setColor() throws RabbitEyesException {
            if(!color.equals("розовые"))
            {
                throw new RabbitEyesException(color);
            }
            else
            {
                Rabbit.name += " с розовыми глазами";
            }
        }
    }

    public static class Skin
    {
        public static String getSkin()
        {
            return skin;
        }
    }
}