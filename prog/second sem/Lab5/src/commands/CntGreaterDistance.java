package commands;

import com.thoughtworks.xstream.converters.extended.ToStringConverter;
import managers.CollectionManager;
import models.Asker;
import models.Route;
import utilities.Console;
import utilities.Execute;

/**
 * команда Cnt_Greater_Distance выводит количество route с distance большей, чем заданная
 */
public class CntGreaterDistance extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public CntGreaterDistance(Console console, CollectionManager collectionManager) {
        super("count greater than distance", "вывести количество элементов коллекции с значением поля distance меньшем введенного");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public Execute apply(String[] arguments) throws Asker.AskBreak {
        if (arguments[1].isEmpty()) return new Execute(false, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
        int distance;
        try { distance = Integer.parseInt(arguments[1].trim()); } catch (NumberFormatException e) { return new Execute(false, "distnce не получена"); }
        int cnt = 0;
        for(Route a : collectionManager.getCollection()){
            if (a.getDistance() > distance){
                cnt++;
            }
        }
        return new Execute(String.valueOf(cnt));
    }
}
