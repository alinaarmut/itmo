package itmo.progg.managers_client;
import itmo.common.description.managers.Request;
import itmo.common.description.managers.Response;
import itmo.common.description.managers.ResponseStatus;
import itmo.ser.commands.managers.Server;

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
    private int reconnectionAttempts;
    private static final int MAX_RECONNECT_ATTEMPTS = 3;
    private int reconnectionTimeout;
    private int maxReconnectionAttempts;
    private static final Logger logger = Logger.getLogger(Client.class.getName());
    public Client(String host, int port,  int reconnectionTimeout, int maxReconnectionAttempts ) {
        this.host = host;
        this.port = port;
        this.reconnectionTimeout = reconnectionTimeout;
        this.maxReconnectionAttempts = maxReconnectionAttempts;

    }


    public Response sendAndAskResponse(Request request) {
        while (true) {
            try {
                if (Objects.isNull(serverWriter) || Objects.isNull(serverReader)) throw new IOException();
                if (request.isEmpty()) return new Response(ResponseStatus.WRONG_ARGUMENTS, "Запрос пустой!");
                serverWriter.writeObject(request);
                serverWriter.flush();
                Response response = (Response) serverReader.readObject();
                reconnectionAttempts = 0;
                return response;
            } catch (IOException e) {
                if (reconnectionAttempts == 0) {
                    connectToServer();
                    reconnectionAttempts++;
                }
                else System.out.println("Соединение с сервером разорвано\n");
                try {
                    reconnectionAttempts++;
                    if (reconnectionAttempts >= maxReconnectionAttempts) {
                        System.out.println("Превышено максимальное количество попыток соединения с сервером\n");
                        return new Response(ResponseStatus.EXIT);
                    }
                    System.out.println("Повторная попытка через " + reconnectionTimeout / 1000 + " секунд\n");
                    Thread.sleep(reconnectionTimeout);
                    connectToServer(); // Повторное подключение к серверу
                } catch (Exception exception) {
                    System.out.println("Попытка соединения с сервером неуспешна\n");
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }



    public void connectToServer(){
        try{
            if (reconnectionAttempts > 0)
                System.out.println("Попытка повторного подключения\n");
            socket = new Socket(host, port);
            System.out.println("Подключение успешно восстановлено\n");

            serverWriter = new ObjectOutputStream(socket.getOutputStream());
           serverReader = new ObjectInputStream(socket.getInputStream());
            System.out.println("Обмен пакетами разрешен\n\n");

        } catch (IllegalArgumentException e){
            System.out.println("Адрес сервера введен некорректно\n");
        } catch (IOException e) {
            System.out.println("Произошла ошибка при соединении с сервером\n" + e.getMessage());
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


}
