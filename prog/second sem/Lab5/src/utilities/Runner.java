package utilities;
import managers.*;
import models.Asker;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Runner {
    private final MyConsole console;
    private final CommandManager commandManager;
    private final List<String> scriptStack = new ArrayList<>();
    private int lengthRecursion = -1;


    public Runner(MyConsole console, CommandManager commandManager) {
        this.console = console;
        this.commandManager = commandManager;
    }


    /**
     * Интерактивный режим
     */
    public void interactiveMode() throws Asker.AskBreak {
        try {
            Execute commandStatus;
            String[] userCommand = {"", ""};

            while (true) {
                console.prompt();
                userCommand = (console.readln().trim() + " ").split(" ", 2);
                userCommand[1] = userCommand[1].trim();


                commandStatus = launchCommand(userCommand);
                if(!userCommand[0].equals("execute_script")) {
                    if (commandStatus.getExitCode()) {
                        commandManager.addToHistory(userCommand[0]);
                    }
                }

                if (commandStatus.getMeessage().equals("exit")) break;
                console.println(commandStatus.getMeessage());
            }
        } catch (NoSuchElementException exception) {
            console.printError("Пользовательский ввод не обнаружен!");
        } catch (IllegalStateException exception) {
            console.printError("Непредвиденная ошибка!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Проверяет рекурсивность выполнения скриптов.
     * @param argument Название запускаемого скрипта
     * @return можно ли выполнять скрипт.
     */
    private boolean checkRecursion(String argument, PushbackReader scriptReader) {
        var recStart = -1;
        var i = 0;
        for (String script : scriptStack) {
            i++;
            if (argument.equals(script)) {
                if (recStart < 0) recStart = i;
                if (lengthRecursion < 0) {
                    console.selectConsoleReader();
                    console.println("Была замечена рекурсия! Введите максимальную глубину рекурсии (0..500)");
                    while (lengthRecursion < 0 || lengthRecursion > 500) {
                        try { console.print("> "); lengthRecursion = Integer.parseInt(console.readln().trim()); } catch (NumberFormatException e) { console.println("длина не распознана"); }
                    }
                    console.selectFileReader(scriptReader);
                }
                if (i > recStart + lengthRecursion || i > 500)
                    return false;
            }
        }
        return true;
    }


    /**
     * Режим для запуска скрипта.
     * @param argument Аргумент скрипта
     * @return Код завершения.
     */
    private Execute scriptMode(String argument) throws Asker.AskBreak {
        int a = 0;
        String[] userCommand = {"", ""};
        StringBuilder executionOutput = new StringBuilder();

        if (!new File(argument).exists()) return new Execute(false, "Файл не существет!");
        if (!Files.isReadable(Paths.get(argument))) return new Execute(false, "Прав для чтения нет!");

        scriptStack.add(argument);
        try (InputStreamReader scriptReader = new InputStreamReader(new FileInputStream(argument))) {
            PushbackReader reader = new PushbackReader(scriptReader);

            Execute commandStatus;

            if (!scriptReader.ready()) throw new NoSuchElementException();
            console.selectFileReader(reader);
            commandManager.addToHistory("execute script ->");
            do {
                userCommand = (console.filereadln().trim() + " ").split(" ", 2);
                userCommand[1] = userCommand[1].trim();
                while (MyConsole.isCanReadln(reader) && userCommand[0].isEmpty()) {
                    userCommand = (console.filereadln().trim() + " ").split(" ", 2);
                    userCommand[1] = userCommand[1].trim();
                }
                executionOutput.append(console.getPrompt() + String.join(" ", userCommand) + "\n");
                var needLaunch = true;
                if (userCommand[0].equals("execute_script")) {
                    needLaunch = checkRecursion(userCommand[1], reader);
                }

                commandStatus = needLaunch ? launchCommand(userCommand) : new Execute("Превышена максимальная глубина рекурсии");
                if (userCommand[0].equals("execute_script")) console.selectFileReader(reader);
                executionOutput.append(commandStatus.getMeessage()+"\n");
                a = reader.read();
                reader.unread(a);
                if(commandStatus.getExitCode()){
                    if(!MyConsole.isCanReadln(reader)) {
                        commandManager.addToHistory(userCommand[0] + "<-");
                    }
                    else{
                        commandManager.addToHistory(userCommand[0]);
                    }
                }
            } while (!commandStatus.getMeessage().equals("exit") && MyConsole.isCanReadln(reader));


            console.selectConsoleReader();
            if (!commandStatus.getExitCode() && !(userCommand[0].equals("execute_script") && !userCommand[1].isEmpty())) {
                executionOutput.append("Проверьте скрипт на корректность введенных данных!\n");
            }
            return new Execute(commandStatus.getExitCode(), executionOutput.toString());
        }
        catch (FileNotFoundException exception) {
            return new Execute(false, "Файл со скриптом не найден!");
        } catch (NoSuchElementException exception) {
            return new Execute(false, "Файл со скриптом пуст!");
        } catch (IllegalStateException exception) {
            console.printError("Непредвиденная ошибка!");
            System.exit(0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        
        } finally {
            scriptStack.remove(scriptStack.size() - 1);
        }
        return new Execute("");
    }

    /**
     * Launchs the command.
     * @param userCommand Команда для запуска
     * @return Код завершения.
     */
    private Execute launchCommand(String[] userCommand) throws IOException, Asker.AskBreak {
        if (userCommand[0].isEmpty()) return new Execute("");
        var command = commandManager.getCommands().get(userCommand[0]);

        if (command == null) return new Execute(false, "Команда '" + userCommand[0] + "' не найдена. Наберите 'help' для справки");

        switch (userCommand[0]) {
            case "execute_script" -> {
                Execute tmp = commandManager.getCommands().get("execute_script").apply(userCommand);
                if (!tmp.getExitCode()) return tmp;
                Execute tmp2 = scriptMode(userCommand[1]);
                return new Execute(tmp2.getExitCode(), tmp.getMeessage()+"\n"+tmp2.getMeessage().trim());
            }
            default -> { return command.apply(userCommand); }
        }
    }
}