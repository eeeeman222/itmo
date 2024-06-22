package commands;
import utilities.Console;
import utilities.SessionHandler;

public class ExecuteScript extends Command {

    private final Console console;

    public ExecuteScript(Console console){
        super("execute_script <file_name>", "исполнить скрипт из указанного файла");
        this.console = console;
    }

    @Override
    public boolean apply(String[] arguments) {
        if(SessionHandler.getCurrentUser() == null){
            console.printError("зарегистрируйтесь или войдите, прежде выполнять команды.\n register or auth");
            return false;
        }
        if (arguments[1].isEmpty()) {
            console.println("Неправильное количество аргументов!");
            console.println("Использование: '" + getName() + "'");
            return false;
        }

        console.println("Выполнение скрипта '" + arguments[1] + "'...");
        return true;
    }
}
