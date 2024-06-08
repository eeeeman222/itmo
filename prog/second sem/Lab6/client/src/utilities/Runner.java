package utilities;

import models.*;
import commands.*;
import genutilities.*;
import managers.*;
import net.UDPClient;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Runner {
    private final MyConsole console;

    private final List<String> scriptStack = new ArrayList<>();
    private int lengthRecursion = -1;

    private final Map<String, Command> commands;
    private final UDPClient client;


    public Runner(UDPClient client, MyConsole console) {
        this.client = client;
        this.console = console;
        this.commands = new HashMap<>() {{
            put(Commands.HELP, new Help(console, client));
            put(Commands.INFO, new Info(console, client));
            put(Commands.SHOW, new Show(console, client));
            put(Commands.ADD, new Add(console, client));
            put(Commands.UPDATE, new Update(console, client));
            put(Commands.REMOVE_BY_ID, new Remove(console, client));
            put(Commands.CLEAR, new Clear(console, client));
            put(Commands.ADD_IF_MAX, new AddIfMax(console, client));
            put(Commands.EXECUTE_SCRIPT, new ExecuteScript(console));
            put(Commands.EXIT, new Exit(console));
            put(Commands.COUNT_GREATER_THAN_DISTANCE, new CntGreaterDistance(console, client));
            put(Commands.REMOVE_LOWER, new RemoveIfMin(console, client));
            put(Commands.PRINT_DESCENDING, new PrintDescending(console, client));
            put(Commands.PRINT_FIELD_DESCENDING_DISTANCE, new PrintDistanceDescending(console, client));
            put(Commands.HISTORY, new History(console, client));
        }};
    }




    /**
     * Интерактивный режим
     */
    public void interactiveMode() {
        try {
            String[] userCommand = {"", ""};

            do {
                console.prompt();
                userCommand = (console.readln().trim() + " ").split(" ", 2);
                userCommand[1] = userCommand[1].trim();

                launchCommand(userCommand);
            } while (!userCommand[0].equals("exit"));

        } catch (NoSuchElementException exception) {
            console.printError("Пользовательский ввод не обнаружен!");
        } catch (IllegalStateException | Asker.AskBreak exception) {
            console.printError("Непредвиденная ошибка!");
        } catch (ClassNotFoundException e) {
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
    private void scriptMode(String argument) throws Asker.AskBreak, ClassNotFoundException {
        int a = 0;
        String[] userCommand = {"", ""};
        File file = new File(argument);

        if (!file.exists()) {
            console.println(argument);
            console.printError("файла со скриптом не существует!");
            if (!Files.isReadable(Paths.get(argument))) {
                console.printError("файл со скриптом не читается");
            }
        }

            scriptStack.add(argument);
            try (InputStreamReader scriptReader = new InputStreamReader(new FileInputStream(argument))) {
                PushbackReader reader = new PushbackReader(scriptReader);

                if (!scriptReader.ready()) throw new NoSuchElementException();
                console.selectFileReader(reader);
                do {
                    userCommand = (console.filereadln().trim() + " ").split(" ", 2);
                    userCommand[1] = userCommand[1].trim();
                    while (console.isCanReadln(reader) && userCommand[0].isEmpty()) {
                        userCommand = (console.readln().trim() + " ").split(" ", 2);
                        userCommand[1] = userCommand[1].trim();
                    }
                    console.println(console.getPrompt() + String.join(" ", userCommand));
                    var needLaunch = true;
                    if (userCommand[0].equals("execute_script")) {
                        needLaunch = checkRecursion(userCommand[1], reader);
                    }

                    if (needLaunch) {
                        launchCommand(userCommand);
                    }
                    if (userCommand[0].equals("execute_script")) console.selectFileReader(reader);
                    a = reader.read();
                    reader.unread(a);
                } while (console.isCanReadln(reader));
                console.selectConsoleReader();
            } catch (FileNotFoundException exception) {
                console.printError("Файл со скриптом не найден!");
            } catch (NoSuchElementException exception) {
                console.printError("Файл со скриптом пуст!");
            } catch (IllegalStateException | IOException exception) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } finally {
                scriptStack.remove(scriptStack.size() - 1);
            }
        }

        /**
         * Launchs the command.
         * @param userCommand Команда для запуска
         * @return Код завершения.
         */

        public void launchCommand (String[]userCommand) throws Asker.AskBreak, ClassNotFoundException {
            var command = commands.get(userCommand[0]);

            if (command == null) {
                console.printError("Команда '" + userCommand[0] + "' не найдена. Наберите 'help' для справки");
                return;
            }

            switch (userCommand[0]) {
                case "exit" -> {
                    commands.get("exit").apply(userCommand);
                }
                case "execute_script" -> {
                    scriptMode(userCommand[1]);
                }
                default -> {
                    command.apply(userCommand);
                }
            }
        }
    }