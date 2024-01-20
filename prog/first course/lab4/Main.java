import MyExceptions.RabbitEyesException;
import MyExceptions.SpacePocketException;
import Objects.*;
import enums.*;



public class Main {
    public static void main(String[] args){
        Human Alice = new Human("Alice");
        Rabbit rabbit = new Rabbit("Кролик");
        Alice.prop("с горя");
        Weather weather = new Weather("Погода");
        weather.influence(Infl.HOT);
        Pocket pocket = new Pocket();
        pocket.setThing("часы");
        Rabbit.Skin skin = new Rabbit.Skin();
        Rabbit.Eyes eyes = rabbit.new Eyes();
        eyes.getColor("розовые"); //передаем цвет глаз
        Wreath Wreath = new Wreath("венок");
        Wreath.choose1();
        Wreath.choose2();
        Human.interaction(Wreath, Action.WEAVE);
        Alice.prop("");
        Flowers Flowers = new Flowers("цветы");
        Human.interaction(Flowers, Action.COLLECT);
        Alice.prop("");
        System.out.print(skin.getSkin());
        try
        {
            eyes.setColor();
        } catch (RabbitEyesException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }

        rabbit.activity(Action.GALLOP);
        rabbit.prop("");
        Alice.time("Сейчас");
        Alice.activity(Action.NOSURPRISE);
        rabbit.time("Потом");
        rabbit.txtact(Action.SAY, "Ай-ай-ай! Я опаздываю!");
        Alice.prop(", вспоминая об этом,");
        Alice.time("Потом");
        Alice.txtact(Action.THINK, "удивиться все-таки стоило.");
        Alice.prop("");
        try
        {
            pocket.isThing();
        }
        catch (SpacePocketException e)
        {
            System.out.println(e.getMessage());
        }

        Rabbit rabbitAnonim = new Rabbit("первый такой Кролик")
        {
            @Override
            public String action(String action)
            {
                return ("%s %s".formatted(action,rabbit.getName()));
            }
        };
        rabbit.prop("");
        rabbit.time("В этот момент");
        rabbit.activity(Action.RUN);
        Alice.activity(Action.JUMP);
        System.out.println(rabbitAnonim.action("это был") + '.');

    }
}