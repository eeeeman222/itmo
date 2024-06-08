package net;

import commands.CommandStarter;
import main.Main;
import servmanagers.CollectionManager;
import servutilities.Pair;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.logging.Logger;

import static java.lang.Math.min;

public class UDPDatagramServer extends UDPRunner {
  private final int PACKET_SIZE = 1000000000;
  private final int DATA_SIZE = PACKET_SIZE - 1;

  private final DatagramSocket datagramSocket;

  private final CollectionManager collectionManager;
  private static final Logger logger = Logger.getLogger(Main.class.getName());

  public UDPDatagramServer(InetAddress address, int port, CommandStarter commandStarter, CollectionManager collectionManager) throws IOException {
    super(new InetSocketAddress(address, port), commandStarter, collectionManager);
    this.datagramSocket = new DatagramSocket(getAddr());
    this.datagramSocket.setReuseAddress(true);
    this.collectionManager = collectionManager;

  }

  @Override
  public Pair<Byte[], SocketAddress> receiveData() throws IOException {
    var received = false;
    var result = new byte[0];
    SocketAddress addr = null;

    while(!received) {
      var data = new byte[PACKET_SIZE];
      var dp = new DatagramPacket(data, PACKET_SIZE);
      datagramSocket.receive(dp);
      int length = dp.getLength();
      System.out.println(length);
      addr = dp.getSocketAddress();
      logger.info("Получены \"" + "данныe от" + dp.getAddress());
      logger.info("Последний байт: " + data[length - 1]);

      if (data[length - 1] == 1) {
        received = true;
        logger.info("Получение данных от " + dp.getAddress() + " окончено");
        result = concatArrays(result, Arrays.copyOf(data, dp.getLength() - 1));
      }
      result = concatArrays(result, Arrays.copyOf(data, dp.getLength()));
    }
    return new Pair<>(toObject(result), addr);
  }

  private byte[] concatArrays(byte[] first, byte[] second) {
    byte[] result = new byte[first.length + second.length];
    System.arraycopy(first, 0, result, 0, first.length);
    System.arraycopy(second, 0, result, first.length, second.length);
    return result;
  }

  private Byte[] toObject(byte[] byteArray) {
    Byte[] result = new Byte[byteArray.length];
    for (int i = 0; i < byteArray.length; i++) {
      result[i] = byteArray[i];
    }
    return result;
  }




  @Override
  public void sendData(byte[] data, SocketAddress addr) throws IOException {
    byte[][] ret = new byte[(int)Math.ceil(data.length / (double)DATA_SIZE)][DATA_SIZE];

    int a = min(data.length, DATA_SIZE);
    int start = 0;
    for(int i = 0; i < ret.length; i++) {
      ret[i] = Arrays.copyOfRange(data, start, start + a);
      start += a;
    }

    logger.info("Отправляется " + ret.length + " чанков...");

    for(int i = 0; i < ret.length; i++) {
      var chunk = ret[i];
      if(data.length <= DATA_SIZE){
        chunk = data;
      }
      if (i == ret.length - 1) {
        byte[] newChunk = new byte[chunk.length + 1];
        System.arraycopy(chunk, 0, newChunk, 0, chunk.length);
        newChunk[newChunk.length - 1] = 1;
        var dp = new DatagramPacket(newChunk, newChunk.length, addr);
        datagramSocket.send(dp);
        logger.info("Последний чанк размером " + newChunk.length + " отправлен на сервер.");
      } else {
        var dp = new DatagramPacket(ByteBuffer.allocate(PACKET_SIZE).put(chunk).array(), PACKET_SIZE, addr);
        datagramSocket.send(dp);
        logger.info("Чанк размером " + chunk.length + " отправлен на сервер.");
      }
    }

    logger.info("Отправка данных завершена");
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
    datagramSocket.close();
  }
}
