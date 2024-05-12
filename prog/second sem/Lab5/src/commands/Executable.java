package commands;
import models.Asker;
import utilities.Execute;

import java.io.IOException;

/**
 * интерфейс для команд
 */
public interface Executable {
    Execute apply(String[] arguments) throws IOException, Asker.AskBreak;
}
