package net;

import commands.CommandStarter;
import main.Main;
import servmanagers.CollectionManager;
import servutilities.Pair;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Math.min;

public class UDPDatagramServer extends UDPRunner {
  private final int PACKET_SIZE = 1000000;
  private final int DATA_SIZE = PACKET_SIZE - 1;
  private DatagramSocket datagramSocket;
  private CollectionManager collectionManager;
  public static final Logger logger = Logger.getLogger(Main.class.getName());

  public UDPDatagramServer(InetAddress address, int port, CommandStarter commandStarter, CollectionManager collectionManager) throws IOException, SocketException {
    super(new InetSocketAddress(address, port), commandStarter);
    this.datagramSocket = new DatagramSocket(getAddr());
    this.datagramSocket.setReuseAddress(true);
  }

  @Override
  public Pair<Byte[], SocketAddress> receiveData() throws IOException {
    boolean received = false;
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    SocketAddress addr = null;

    while (!received) {
      byte[] data = new byte[PACKET_SIZE];
      DatagramPacket dp = new DatagramPacket(data, PACKET_SIZE);
      datagramSocket.receive(dp);
      int length = dp.getLength();
      addr = dp.getSocketAddress();
      logger.info("Получены данные от " + dp.getAddress());

      if (data[length - 1] == 1) {
        received = true;
        baos.write(data, 0, length - 1);
        logger.info("Получение данных от " + dp.getAddress() + " окончено");
      } else {
        baos.write(data, 0, length);
      }
    }
    return new Pair<>(toObject(baos.toByteArray()), addr);
  }

  private Byte[] toObject(byte[] byteArray) {
    Byte[] result = new Byte[byteArray.length];
    for (int i = 0; i < byteArray.length; i++) {
      result[i] = byteArray[i];
    }
    return result;
  }

  @Override
  public void sendData(byte[] data, SocketAddress addr) {
    byte[][] ret = new byte[(int) Math.ceil(data.length / (double) DATA_SIZE)][DATA_SIZE];

    int a = Math.min(data.length, DATA_SIZE);
    int start = 0;
    for (int i = 0; i < ret.length; i++) {
      ret[i] = Arrays.copyOfRange(data, start, start + a);
      start += a;
    }

    logger.info("Отправляется " + ret.length + " чанков...");

    for (int i = 0; i < ret.length; i++) {
      final byte[] chunk = ret[i];
      final boolean isLastChunk = (i == ret.length - 1);

      new Thread(() -> {
        try {
          if (isLastChunk) {
            byte[] newChunk = new byte[chunk.length + 1];
            System.arraycopy(chunk, 0, newChunk, 0, chunk.length);
            newChunk[newChunk.length - 1] = 1;
            sendChunk(newChunk, addr);
          } else {
            sendChunk(chunk, addr);
          }
        } catch (IOException e) {
          logError("Ошибка отправки данных: " + e.toString(), e);
        }
      }).start();
    }

    logger.info("Отправка данных завершена");
  }

  private void sendChunk(byte[] chunk, SocketAddress addr) throws IOException {
    synchronized (datagramSocket) {
      DatagramPacket dp = new DatagramPacket(chunk, chunk.length, addr);
      datagramSocket.send(dp);
      logInfo("Чанк размером " + chunk.length + " отправлен клиенту " + addr);
    }
  }

  @Override
  public void connectToClient(SocketAddress addr) throws SocketException {
    datagramSocket.connect(addr);
  }

  @Override
  public void disconnectFromClient() {
    datagramSocket.disconnect();
  }

  @Override
  public void close() {
    if (datagramSocket != null && !datagramSocket.isClosed()) {
      datagramSocket.close();
    }
  }

  public void logError(String message, Throwable e) {
    logger.log(Level.SEVERE, message, e);
  }

  public void logInfo(String message) {
    logger.info(message);
  }
}
