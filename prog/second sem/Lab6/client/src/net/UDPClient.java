package net;

import requests.Request;
import responses.Response;
import main.Main;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Arrays;
import java.util.logging.Logger;

public class UDPClient {
    private final int PACKET_SIZE = 1000000000;
    private final int DATA_SIZE = PACKET_SIZE - 1;

    private final DatagramChannel client;
    private final InetSocketAddress addr;
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public UDPClient(InetAddress address, int port) throws IOException {
        this.addr = new InetSocketAddress(address, port);
        this.client = DatagramChannel.open().connect(addr);
        this.client.configureBlocking(false);
        logger.info("DatagramChannel подключен к " + addr);
    }

    public Response sendAndReceiveCommand(Request request) throws IOException, ClassNotFoundException {
        byte[] data = serializeRequest(request);
        byte[] responseBytes = sendAndReceiveData(data);
        Response response = deserializeResponse(responseBytes);
        logger.info("Получен ответ от сервера: " + response);
        return response;
    }

    private byte[] serializeRequest(Request request) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(request);
        oos.flush();
        return baos.toByteArray();
    }

    private Response deserializeResponse(byte[] responseBytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bais = new ByteArrayInputStream(responseBytes);
        ObjectInputStream ois = new ObjectInputStream(bais);
        return (Response) ois.readObject();
    }

    private void sendData(byte[] data) throws IOException {
        int chunkCount = (int) Math.ceil(data.length / (double) DATA_SIZE);
        byte[][] chunks = new byte[chunkCount][DATA_SIZE];

        int start = 0;
        for (int i = 0; i < chunkCount; i++) {
            int end = Math.min(start + DATA_SIZE, data.length);
            chunks[i] = Arrays.copyOfRange(data, start, end);
            start += DATA_SIZE;
        }

        logger.info("Отправляется " + chunkCount + " чанков...");

        for (int i = 0; i < chunkCount; i++) {
            byte[] chunk = chunks[i];
            if (i == chunkCount - 1) {
                byte[] lastChunk = new byte[chunk.length + 1];
                System.arraycopy(chunk, 0, lastChunk, 0, chunk.length);
                lastChunk[lastChunk.length - 1] = 1;
                sendChunk(lastChunk);
                logger.info("Последний чанк размером " + lastChunk.length + " отправлен на сервер.");
            } else {
                byte[] answer = new byte[chunk.length + 1];
                System.arraycopy(chunk, 0, answer, 0, chunk.length);
                answer[answer.length - 1] = 0;
                sendChunk(answer);
                logger.info("Чанк размером " + answer.length + " отправлен на сервер.");
            }
        }
    }

    private void sendChunk(byte[] chunk) throws IOException {
        logger.info("Отправка чанка: " + Arrays.toString(chunk));
        client.send(ByteBuffer.wrap(chunk), addr);
        logger.info("Чанк отправлен.");
    }

    private byte[] receiveData() throws IOException {
        boolean received = false;
        byte[] result = new byte[0];

        while (!received) {
            byte[] data = receiveData(PACKET_SIZE);
            int length = data.length;
            logger.info("Получено \"" + new String(data, 0, length) + "\"");
            logger.info("Последний байт: " + data[length - 1]);

            if (data[length - 1] == 1) {
                received = true;
                logger.info("Получение данных окончено");
                result = concatArrays(result, Arrays.copyOf(data, length - 1));
            } else {
                result = concatArrays(result, Arrays.copyOf(data, length));
            }
        }

        return result;
    }

    private byte[] concatArrays(byte[] first, byte[] second) {
        byte[] result = new byte[first.length + second.length];
        System.arraycopy(first, 0, result, 0, first.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    private byte[] receiveData(int bufferSize) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(bufferSize);
        SocketAddress address = null;

        address = client.receive(buffer);

        while (address == null) {
            address = client.receive(buffer);
        }

        buffer.flip();
        byte[] receivedData = new byte[buffer.remaining()];
        buffer.get(receivedData);

        return receivedData;
    }

    private byte[] sendAndReceiveData(byte[] data) throws IOException {
        sendData(data);
        return receiveData();
    }
}
