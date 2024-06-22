package commands;

import managers.Asker;

import java.io.IOException;

/**
 * интерфейс для команд
 */
public interface Executable {
    boolean apply(String[] arguments) throws IOException, ClassNotFoundException, Asker.AskBreak;
}
