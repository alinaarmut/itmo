package itmo.ser.commands.managers;

import itmo.common.description.managers.Request;
import itmo.common.description.managers.Response;
import itmo.common.description.managers.ResponseStatus;
import itmo.ser.commands.dateBase.DataBaseHandler;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.channels.SocketChannel;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.logging.Logger;

public class ConnectionManager implements Runnable {
    private final CommandManager commandManager;
    static final Logger logger = Logger.getLogger(ConnectionManager.class.getName());
    private final DataBaseHandler dataBaseHandler;
    private final SocketChannel clientSocket;
    private static final ExecutorService CachedThreadPool = Executors.newCachedThreadPool();

    private static final ForkJoinPool forkJoinPool = new ForkJoinPool();

    public ConnectionManager(CommandManager commandManager, SocketChannel clientSocket,  DataBaseHandler dataBaseHandler) {
        this.commandManager = commandManager;
        this.clientSocket = clientSocket;
        this.dataBaseHandler = dataBaseHandler;

    }

    @Override
    public void run() {
        Request userRequest = null;
        Response responseToUser = null;
        try {
            ObjectInputStream clientReader = new ObjectInputStream(clientSocket.socket().getInputStream());
            ObjectOutputStream clientWriter = new ObjectOutputStream(clientSocket.socket().getOutputStream());
            while (true) {
                userRequest = (Request) clientReader.readObject();
                logger.info("Received request: " + userRequest.toString());
                if (!dataBaseHandler.loginUser(userRequest.getUser())
                        && !userRequest.getCommandName().equals("register")) {
                    System.out.println("Юзер не одобрен");
                    responseToUser = new Response(ResponseStatus.LOGIN_FAILED, "Неверный пользователь!");
                    submitNewResponse(new ConnectionManagerPool(responseToUser, clientWriter));
                    logger.info("Sent response: " + responseToUser.toString());

                } else {
                    FutureManager.addNewCachedThreadPoolFuture(CachedThreadPool.submit(new RequestHandler(commandManager, userRequest, clientWriter)));
                }
            }
        } catch (IOException ex) {
            System.out.println("Произошла ошибка. Соединение уже было закрыто");
            //делать выход
            System.out.println(1);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

            public static void submitNewResponse(ConnectionManagerPool connectionManagerPool){
                forkJoinPool.submit(() -> {
                    try {
                        connectionManagerPool.objectOutputStream().writeObject(connectionManagerPool.response());
                        connectionManagerPool.objectOutputStream().flush();
                        logger.info("Ответ отправлен успешно");
                    } catch (IOException e) {
                        System.out.println("Не удалось отправить ответ");
                    }
                });
            }




}
