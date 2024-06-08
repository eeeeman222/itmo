package managers;

import commands.Command;

import java.util.ArrayDeque;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Queue;

public class CommandManager {
    private final Map<String, Command> commands = new LinkedHashMap<>();
    private final Queue<String> commandHistory = new ArrayDeque<>(6);


    public void register(String commandName, Command command) {
        commands.put(commandName, command);
    }

    public Map<String, Command> getCommands() {
        return commands;
    }
    public Queue<String> getCommandHistory() {
        return commandHistory;
    }

    public void addToHistory(String command) {
        if(commandHistory.size() < 6) {
            commandHistory.add(command);
        }
        else{
            commandHistory.remove();
            commandHistory.add(command);
        }
    }

    public void clear() {
        for (var e:commands.keySet().toArray(new String[0]))
            if (e.charAt(0) == '$')
                commands.remove(e);
    }
}

//history