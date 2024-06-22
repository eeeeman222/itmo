package net;

import commands.CommandStarter;
import main.Main;
import requests.Request;
import responses.NoCommandResponse;
import responses.Response;
import servmanagers.CollectionManager;
import servutilities.Pair;

import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.concurrent.*;
import java.util.logging.Level;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 * UDP обработчик запросов
 * @author maxbarsukov
 */
abstract class UDPRunner {
    private static final int READ_POOL_SIZE = 4;

    private final InetSocketAddress addr;
    private final CommandStarter commandHandler;
    private final ExecutorService readPool;

    private final ExecutorService processPool;

    public static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    private boolean running = true;

    public UDPRunner(InetSocketAddress addr, CommandStarter commandHandler) {
        this.addr = addr;
        this.commandHandler = commandHandler;
        this.readPool = Executors.newFixedThreadPool(READ_POOL_SIZE);
        this.processPool = new ForkJoinPool();
    }

    public InetSocketAddress getAddr() {
        return addr;
    }

    /**
     * Получает данные с клиента.
     * Возвращает пару из данных и адреса клиента
     */
    public abstract Pair<Byte[], SocketAddress> receiveData() throws IOException;

    /**
     * Отправляет данные клиенту
     */
    public abstract void sendData(byte[] data, SocketAddress addr) throws IOException;

    public abstract void connectToClient(SocketAddress addr) throws SocketException;

    public abstract void disconnectFromClient();
    public abstract void close();

    public void run() {
        LOGGER.info("Сервер запущен по адресу " + addr);

        readPool.submit(() -> {
            while (running) {
                Pair<Byte[], SocketAddress> dataPair;
                try {
                    dataPair = receiveData();
                } catch (Exception e) {
                    LOGGER.info("Ошибка получения данных : " + e.toString());
                    disconnectFromClient();
                    continue;
                }

                var datac = dataPair.getFirst();
                var addrc = dataPair.getSecond();

                try {
                    connectToClient(addrc);
                    LOGGER.info("Соединено с " + addrc);
                } catch (Exception e) {
                    LOGGER.info("Ошибка соединения с клиентом : " + e.toString());
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
                } catch (IOException | ClassNotFoundException e) {
                    LOGGER.info("Невозможно десериализовать объект запроса.");
                    return;
                }

                processPool.submit(() -> {
                    Response response = null;
                    try {
                        response = commandHandler.handle(request);
                    } catch (Exception e) {
                        LOGGER.info("Ошибка выполнения команды : " + e.toString());
                    }
                    if (response == null) response = new NoCommandResponse(request.getName());

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
                        oos.writeObject(response);
                    } catch (IOException e) {
                        LOGGER.info("Ошибка сериализации ответа: " + e.toString());
                        return;
                    }

                    byte[] data = baos.toByteArray();
                    LOGGER.info("Ответ: " + response);

                    new Thread(() -> {
                        try {
                            sendData(data, addrc);
                            LOGGER.info("Отправлен ответ клиенту " + addrc);
                        } catch (Exception e) {
                            LOGGER.info("Ошибка ввода-вывода : " + e.toString());
                        }
                    }).start();
                });

                disconnectFromClient();
                LOGGER.info("Отключение от клиента " + addrc);
            }
            close();
        });
    }

    public void stop() {
        running = false;
    }
}