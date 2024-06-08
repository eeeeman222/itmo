package net;
import commands.CommandStarter;
import responses.*;
import requests.*;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.*;
import main.Main;
import servmanagers.CollectionManager;
import servutilities.Pair;

/**
 * UDP обработчик запросов
 */
abstract class UDPRunner {
    private final InetSocketAddress addr;
    private final CommandStarter commandStarter;

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    private boolean running = true;

    private final CollectionManager collectionManager;

    public UDPRunner(InetSocketAddress addr, CommandStarter commandStarter, CollectionManager collectionManager) {
        this.addr = addr;
        this.commandStarter = commandStarter;
        this.collectionManager = collectionManager;
    }

    public InetSocketAddress getAddr() {
        return addr;
    }

    public abstract Pair<Byte[], SocketAddress> receiveData() throws IOException;

    public abstract void sendData(byte[] data, SocketAddress addr) throws IOException;

    public abstract void connectToClient(SocketAddress addr) throws SocketException;

    public abstract void disconnectFromClient();
    public abstract void close();

    public void run() throws IOException {
        logInfo("Сервер запущен по адресу " + addr);

        BufferedInputStream bf = new BufferedInputStream(System.in);
        Scanner scanner = new Scanner(System.in);


        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    try {
                        checkCommand(line);
                    } catch (IOException e) {
                        logError("что-то с командой не так...", e);
                    }
                }
            }
        }, 0, 100);


        while (running) {

            Pair<Byte[], SocketAddress> dataPair;
            try {
                dataPair = receiveData();
            } catch (Exception e) {
                logError("Ошибка получения данных : " + e.toString(), e);
                disconnectFromClient();
                continue;
            }

            var datac = dataPair.getFirst();
            
            
            var addrc = dataPair.getSecond();

            try {
                connectToClient(addrc);
                logInfo("Соединено с " + addrc);
            } catch (Exception e) {
                logError("Ошибка соединения с клиентом : " + e.toString(), e);
            }
            Request request;
            try {
                byte[] dataBytes = new byte[datac.length];
                for (int i = 0; i < datac.length; i++) {
                    dataBytes[i] = datac[i];
                }
                ByteArrayInputStream bais = new ByteArrayInputStream(dataBytes);
                ObjectInputStream ois = new ObjectInputStream(bais);
                request = (Request) ois.readObject();
                logInfo("Обработка " + request + " из " + addrc);
            } catch (IOException | ClassNotFoundException e) {
                logError("Невозможно десериализовать объект запроса.", e);
                disconnectFromClient();
                continue;
            }


            Response response = null;
            try {
                response = commandStarter.handle(request);
            } catch (Exception e) {
                logError("Ошибка выполнения команды: " + e.toString(), e);
                response = new NoCommandResponse(request.getName());
            }

            if (response == null) {
                response = new NoCommandResponse(request.getName());
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
                oos.writeObject(response);
            } catch (IOException e) {
                logError("Ошибка сериализации ответа: " + e.toString(), e);
                return;
            }

            byte[] data = baos.toByteArray();
            logInfo("Ответ: " + response);

            try {
                sendData(data, addrc);
                logInfo("Отправлен ответ клиенту " + addrc);
            } catch (Exception e) {
                logError("Ошибка ввода-вывода : " + e.toString(), e);
            }

            disconnectFromClient();
            logInfo("Отключение от клиента " + addrc);
        }

        close();
    }

    public void logError(String message, Throwable e) {
        LOGGER.log(Level.SEVERE, message, e);
    }

    public void logInfo(String message) {
        LOGGER.info(message);
    }
    public void checkCommand(String line) throws IOException {
        if (line.equals("save")) {
            collectionManager.save();
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