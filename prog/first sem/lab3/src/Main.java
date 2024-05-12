import Objects.*;
import enums.*;



public class Main {
    public static void main(String[] args){
        Human Alice = new Human("Alice");
        Rabbit Rabbit = new Rabbit("Кролик");
        Alice.prop("с горя");
        Weather weather = new Weather("Погода");
        weather.influence(Infl.HOT);
        Wreath Wreath = new Wreath("венок");
        Human.interaction(Wreath, Action.WEAVE);
        Alice.prop("");
        Flowers Margarines = new Flowers("маргаринки");
        Human.interaction(Margarines, Action.COLLECT);
        Alice.prop("");
        Rabbit.prop("с розовыми глазами");
        Rabbit.activity(Action.GALLOP);
        Rabbit.prop("");
        Alice.time("Сейчас");
        Alice.activity(Action.NOSURPRISE);
        Rabbit.time("Потом");
        Rabbit.txtact(Action.SAY, "Ай-ай-ай! Я опаздываю!");
        Alice.prop(", вспоминая об этом,");
        Alice.time("Потом");
        Alice.txtact(Action.THINK, "удивиться все-таки стоило.");

    }
}
