package main;

import net.UDPClient;
import utilities.MyConsole;
import utilities.Runner;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Logger;

/**
 * Главный класс клиентского приложения.
 */
public class Main {
    private static final int PORT = 62888;
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        var console = new MyConsole();
        try {
            var client = new UDPClient(InetAddress.getLocalHost(), PORT);
            var cli = new Runner(client, console);

            cli.interactiveMode();
        } catch (IOException e) {
            logger.info("Невозможно подключиться к серверу.");
            System.out.println("Невозможно подключиться к серверу!");
        }
    }
}
