import commands.*;
import managers.CollectionManager;
import managers.CommandManager;
import managers.ParseManager;
import models.Asker;
import models.Route;
import utilities.Console;
import utilities.MyConsole;
import utilities.Runner;
import java.util.LinkedList;
import java.util.List;

public class Main {



    public static void main(String[] args) throws Asker.AskBreak {
        String filename = System.getenv("path");
        if (filename == null) {
            System.out.println("Переменная окружения path не установлена.");
            return;
        }

        var console = new MyConsole();
        var parsemanager = new ParseManager(filename, console);
        var collectionManager = new CollectionManager(parsemanager);
        collectionManager.init();
        /**
         * определяем команды
         */
        var commandManager = new CommandManager() {

            {
            register("add", new Add(console, collectionManager));
            register("show", new Show(console, collectionManager));
            register("exit", new Exit(console));
            register("help", new Help(console, this));
            register("save", new Save(console, collectionManager));
            register("clear", new Clear(console, collectionManager));
            register("update", new Update(console, collectionManager));
            register("remove", new Remove(console, collectionManager));
            register("execute_script", new ExecuteScript(console));
            register("history", new History(console, this));
            register("add_if_max", new AddIfMax(console, collectionManager));
            register("remove_lower", new RemoveIfMin(console, collectionManager));
            register("count_greater_than_distance", new CntGreaterDistance(console, collectionManager));
            register("print_descending", new PrintDescending(console, collectionManager));
            register("print_field_descending_distance", new PrintDistanceDescending(console, collectionManager));
            register("info", new Info(console, collectionManager));
        }};
        new Runner(console, commandManager).interactiveMode();
    }
}