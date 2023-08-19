package itmo.progg.managers_client;
import itmo.common.description.managers.Request;
import itmo.common.description.managers.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Objects;
import java.util.logging.Logger;

public class Client {
    private final String host;
    private final int port;
    private Socket socket;
    private ObjectOutputStream serverWriter;
    private ObjectInputStream serverReader;
    private static final Logger Clientlogger = Logger.getLogger(Client.class.getName());
    public Client(String host, int port ) {
        this.host = host;
        this.port = port;

    }


    public Response sendAndAskResponse(Request request) {
     try {

            if (Objects.isNull(serverWriter) || Objects.isNull(serverReader)) {
                throw new IOException();
            }
         serverWriter.writeObject(request);
         serverWriter.flush();
         Response response = (Response) serverReader.readObject();
         return response;
        } catch (IOException | ClassNotFoundException e) {
         System.out.println("Ошибка с отправкой ответа");;
     }
        return null;
    }
    public void connectToServer() {

        try {
            socket = new Socket(host, port);
            serverWriter = new ObjectOutputStream(socket.getOutputStream());
            serverReader = new ObjectInputStream(socket.getInputStream());
            Clientlogger.info("Новый клиент подключился");
        } catch (IllegalArgumentException | IOException e) {
            System.out.println("не удалось подключиться к серверу");

        }
    }


    public void disconnectFromServer() {
        try {
            socket.close();
            serverWriter.close();
            serverReader.close();
            Clientlogger.info("Клиент отключился от сервера");
        } catch (IOException e) {
            System.out.println("Ошибка при закрытии соединения с сервером: " + e.getMessage());
        }
    }
}


