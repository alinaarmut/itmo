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
    private int reconnectAttempts = 0;
    private static final int MAX_RECONNECT_ATTEMPTS = 3;

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
            reconnectAttempts = 0;
            return response;
        } catch (ClassNotFoundException e) {
            System.out.println("Ошибка с отправкой ответа");
        } catch (IOException e) {
            if (reconnectAttempts == 0) {
                connectToServer();
                reconnectAttempts++;
            } else {
                if (reconnectAttempts <= MAX_RECONNECT_ATTEMPTS) {
                    for (int i = 0; i < MAX_RECONNECT_ATTEMPTS - reconnectAttempts; i++) {
                        reconnectToServer();
                        reconnectAttempts++;
                        if (reconnectAttempts >= MAX_RECONNECT_ATTEMPTS) {
                            System.out.println("Превышено максимальное количество попыток соединения с сервером");
                            break;
                        }
                        try {
                            serverWriter.writeObject(request);
                            serverWriter.flush();
                            Response response = (Response) serverReader.readObject();
                            return response;
                        } catch (IOException reconnectError) {
                            System.out.println("Ошибка при переподключении к серверу.... ");
                        } catch (ClassNotFoundException ex) {

                        }
                    }
                }
            }
        }
        return null;
    }

    public void connectToServer() {

        try {
            socket = new Socket(host, port);
            serverWriter = new ObjectOutputStream(socket.getOutputStream());
            serverReader = new ObjectInputStream(socket.getInputStream());
        } catch (IllegalArgumentException | IOException e) {
            System.out.println("не удалось подключиться к серверу");

        }
    }


    public void disconnectFromServer() {
        try {
            socket.close();
            serverWriter.close();
            serverReader.close();
        } catch (IOException e) {

        }
    }
    public void reconnectToServer() {
        try {
            socket = new Socket(host, port);
            serverWriter = new ObjectOutputStream(socket.getOutputStream());
            serverReader = new ObjectInputStream(socket.getInputStream());
        } catch (IllegalArgumentException | IOException e) {
        }
    }

}
