package itmo.ser.commands.managers;

import itmo.common.description.managers.Request;
import itmo.common.description.managers.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Scanner;
import java.util.logging.Logger;

public class Server {


    private int port;
    private ServerSocketChannel serverSocketChannel;
    private SocketChannel socketChannel;
    private RequestHandler requestHandler;
    private static final Logger logger = Logger.getLogger(Server.class.getName());



    public Server(int port, RequestHandler handler) {
        this.port = port;
        this.requestHandler = handler;

    }

    public void run() {
        try {
            openServerSocket();
            startSaveCommandListener();
            logger.info("Создано соединение с клиентом");
            while (true) {
                try (SocketChannel clientSocket = connectToClient()) {
                    if (!processClientRequest(clientSocket))
                        break;
                } catch (SocketTimeoutException ignored) {
                } catch (IOException exception) {
                    logger.info("Произошла ошибка при попытке завершить соединение с клиентом!");
                    System.out.println("Произошла ошибка при попытке завершить соединение с клиентом!");
                }

            }

        } catch (Exception e) {
            System.out.println("Произошла ошибка в запуске сервера");
        }
    }

    private SocketChannel connectToClient() {
        try {
            socketChannel = serverSocketChannel.socket().accept().getChannel();
            return socketChannel;
        } catch (IOException e) {
           // System.out.println("Произошла ошибка в соединение с клиентом");
        }
        return null;
    }

    private void openServerSocket() {
        boolean socketBound = false;
        Scanner scanner = new Scanner(System.in);

        while (!socketBound) {
            try {
                SocketAddress socketAddress = new InetSocketAddress(port);
                serverSocketChannel = ServerSocketChannel.open();
                serverSocketChannel.bind(socketAddress);
                socketBound = true;
            } catch (BindException e) {
                System.out.println("Ошибка: Адрес уже занят. Введите другой номер порта:");
                logger.info("Ошибка: Адрес уже занят. Введите другой номер порта:");
                port = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Ошибка при открытии серверного сокета.");
            }
        }
    }


    private boolean processClientRequest(SocketChannel clientSocket) {
        Request userRequest = null;
        Response responseToUser;
        try {
            ObjectInputStream clientReader = new ObjectInputStream(clientSocket.socket().getInputStream());
            ObjectOutputStream clientWriter = new ObjectOutputStream(clientSocket.socket().getOutputStream());
            while (true) {
                userRequest = (Request) clientReader.readObject();
                if (userRequest.getCommandName().equals("exit")) {
                    responseToUser = new Response("Выход из программы.");
                    clientWriter.writeObject(responseToUser);
                    clientWriter.flush();
                    break;
                } else {
                    responseToUser = requestHandler.handle(userRequest);
                    clientWriter.writeObject(responseToUser);
                    clientWriter.flush();
                    clientSocket.close();
                    break;
                }
            }

            clientSocket.close();
            return true;
        } catch (IOException | ClassNotFoundException e) {
        }
        return false;
    }



    private void stop() {
        try {
            if (socketChannel != null)
                socketChannel.close();
            logger.info("Произошло закрытие соккета");
            if (serverSocketChannel != null)
                serverSocketChannel.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());;
        }
    }
    public void startSaveCommandListener() {
        Thread saveCommandListener = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String command = scanner.nextLine().trim();

                if (command.equalsIgnoreCase("save")) {
                    Converter converter = new Converter();
                    converter.writeCollection(CollectionManager.movies);
                    System.out.println("Коллекция сохранена в файл.");
                }
                    if (command.equalsIgnoreCase("exit_server")) {
                        stop();
                        System.exit(0);


                } else {
                    System.out.println("Неизвестная команда.");
                }
            }
        });
        saveCommandListener.start();
    }

}