package main;

import commands.*;
import genutilities.Commands;
import net.UDPDatagramServer;
import servmanagers.CollectionManager;
import servmanagers.CommandManager;
import servmanagers.ParseManager;
import servutilities.Validator;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.channels.DatagramChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

import static jdk.internal.net.http.common.Log.logError;

/**
 * Серверная часть приложения.
 */
public class Main {
    public static final int PORT = 62888;

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());


    public static void main(String[] args) throws IOException {
        /*

        if (args.length == 0) {
            System.out.println("Введите имя загружаемого файла как аргумент командной строки");
            System.exit(1);
        }
         */

        var parseManager = new ParseManager("xmlh.xml");
        var collectionManager = new CollectionManager(parseManager);
        collectionManager.init();
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
        }};

        try {
            var runner = new UDPDatagramServer(InetAddress.getLocalHost(), PORT, new CommandStarter(commandManager), collectionManager);
            runner.run();
        } catch (SocketException e) {
            logFatal("Случилась ошибка сокета", e);
        } catch (UnknownHostException e) {
            logFatal("Неизвестный хост", e);
        }
    }

    public static void logFatal(String message, Exception e) {
        LOGGER.log(Level.SEVERE, message, e);
    }
}