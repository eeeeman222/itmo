package main;

import commands.*;
import genutilities.Commands;
import net.UDPDatagramServer;
import servmanagers.CollectionManager;
import servmanagers.CommandManager;
import servmanagers.JDBCManager;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Серверная часть приложения.
 */
public class Main {
    public static final int PORT = 62888;

    public static final Logger LOGGER = Logger.getLogger(Main.class.getName());


    public static void main(String[] args) throws IOException {

        ExecutorService executorService = Executors.newFixedThreadPool(2);


        /*

        if (args.length == 0) {
            System.out.println("Введите имя загружаемого файла как аргумент командной строки");
            System.exit(1);
        }
         */

        var collectionManager = new CollectionManager();
        var commandManager = new CommandManager() {{
            register(Commands.HELP, new Help(this));
            register(Commands.INFO, new Info(collectionManager));
            register(Commands.SHOW, new Show(collectionManager));
            register(Commands.ADD, new Add(collectionManager));
            register(Commands.UPDATE, new Update(collectionManager));
            register(Commands.REMOVE_BY_ID, new Remove(collectionManager));
            register(Commands.CLEAR, new Clear(collectionManager));
            register(Commands.COUNT_GREATER_THAN_DISTANCE, new CntGreaterDistance(collectionManager));
            register(Commands.ADD_IF_MAX, new AddIfMax(collectionManager));
            register(Commands.REMOVE_LOWER, new RemoveLower(collectionManager));
            register(Commands.PRINT_DESCENDING, new PrintDescending(collectionManager));
            register(Commands.PRINT_FIELD_DESCENDING_DISTANCE, new PrintDistanceDescending(collectionManager));
            register(Commands.HISTORY, new History(this));
            register(Commands.REGISTER, new Register(collectionManager));
            register(Commands.AUTHENTICATE, new Authorizate(collectionManager));
        }};

        try {
            JDBCManager.getAllRoutes(collectionManager);
            JDBCManager.getAllUsers(collectionManager);
            var runner = new UDPDatagramServer(InetAddress.getLocalHost(), PORT, new CommandStarter(commandManager), collectionManager);
            BufferedInputStream bf = new BufferedInputStream(System.in);
            Scanner scanner = new Scanner(System.in);

            executorService.submit(() -> {
                runner.run();
            });


            executorService.submit(() -> {
                while (true) {

                    if (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        try {
                            checkCommand(line);
                        } catch (IOException e) {
                            LOGGER.info("что-то с командой не так...");
                        }
                    }
                }
            });

            try {
                executorService.shutdown();
                if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                    executorService.shutdownNow();
                }
            } catch (InterruptedException e) {
                executorService.shutdownNow();
            }
        } catch (SocketException e) {
            logFatal("Случилась ошибка сокета", e);
        } catch (UnknownHostException e) {
            logFatal("Неизвестный хост", e);
        }
    }

    public static void logFatal(String message, Exception e) {
        LOGGER.log(Level.SEVERE, message, e);
    }

    public static void checkCommand(String line) throws IOException {
        if (line.equals("save")) {
            LOGGER.info("Объекты успешно сохранены");
        } else if (line.equals("exit")) {
            LOGGER.info("⠄⠄⠄⠄⠄⠄⢀⣠⡶⠖⠛⠉⠉⠉⠉⠉⠛⠲⣦⣄⠄⠄⠄⠄\n" +
                    "⠄⠄⠄⠄⣤⠖⠋⠁⠄⠄⠄⠄⢀⣴⣿⠛⠙⠛⢷⣤⣈⢿⠄⠄\n" +
                    "⠄⠄⣴⠋⠄⠄⠄⠄⣀⣤⣶⠶⠚⠛⠁⠄⠄⠄⠄⠄⠄⠄⣿⠄\n" +
                    "⢀⡟⣠⣶⠖⠛⠉⢁⣠⣴⣶⢶⡄⠄⠺⣯⣭⣭⣭⣿⠿⠗⢸⡆\n" +
                    "⣾⠄⠄⠄⣴⣞⣉⣈⣿⡿⠛⠁⠄⠄⠄⠄⣻⣦⠶⠛⠉⠙⢿⡇\n" +
                    "⣿⠄⠄⠄⠄⠄⠄⠄⠄⠄⢀⣠⣤⠶⠛⠉⠄⠄⠄⠄⠄⡶⢻⠁\n" +
                    "⣿⠄⠄⠄⠄⠄⠛⠛⠛⠉⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⢰⡇⣿⠄\n" +
                    " ⠘⣆⠄⠄⠄⠄⠄⠄⠄⢀⠄⠄⠄⠄⠄⠄⠄⠄⠄⢠⡟⣼⠃⠄\n" +
                    "⠄⠹⣄⠄⠄⠄⠄⠄⠄⠄⠛⣦⣀⠄⠄⠄⠄⣠⡶⠋⣼⠃⠄⠄\n" +
                    "⠄⠄⠈⠛⣦⡀⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⣠⡾⠋⠄⠄⠄⠄\n" +
                    "⠄⠄⠄⠄⠄⠈⠉⠛⠛⠶⣤⣿⣿⣴⣶⠛⠉⠄⠄⠄⠄⠄⠄⠄\n" +
                    "⠄⠄⠄⠄⠄⠄⠄⠄⠄⣰⠋⢸⠄⠙⢷⡀⠄⠄⠄⠄⠄⠄⠄⠄\n" +
                    "⠄⠄⠄⠄⠄⠄⠄⠄⣾⠁⠄⢸⠄⠄⠄⠈⢷⡀⠄⠄⠄⠄⠄⠄\n" +
                    "⠄⠄⠄⠄⠄⠄⢠⡟⠄⠄⠄⢸⡆⠄⠄⠄⠄⠘⢶⡀⠄⠄⠄⠄\n" +
                    "⠄⠄⠄⠄⠄⣾⠃⠄⠄⠄⠄⠄⣇⠄⠄⠄⠄⠄⠄⠻⡄⠄⠄⠄\n" +
                    "⠄⠄⠄⢀⡿⠄⠄⠄⠄⠄⠄⣀⣿⣀⣀⣀⣀⣀⣀⡀⢹⣦⣤⠄\n" +
                    "⢀⣤⣶⣿⣿⣷⣶⠟⠛⠉⠄⠄⢸⡄⠄⠄⠉⠙⠛⠿⣿⣿⣦⢻\n" +
                    "⠄⣸⠃⢿⠄⠄⠄⠄⠄⠄⠄⠄⠄⡇⠄⠄⠄⠄⠄⠄⠘⣿⠄⠄".indent(1));
            LOGGER.info("Увидимся на допсе...");
            System.exit(0);
        } else System.out.println("боже тут всего две команды.....");
    }
}