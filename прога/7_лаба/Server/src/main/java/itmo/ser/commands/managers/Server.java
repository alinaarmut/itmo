package itmo.ser.commands.managers;
import itmo.ser.commands.dateBase.DataBaseHandler;
import itmo.ser.commands.exceptions.ConnectionErrorException;
import itmo.ser.commands.exceptions.OpeningServerException;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class Server {


    private int port;
    private ServerSocketChannel serverSocket;
    private SocketChannel socketChannel;
    private int soTimeout;
    private final DataBaseHandler dataBaseHandler;
    private static final Logger logger = Logger.getLogger(Server.class.getName());
    private final ExecutorService fixedThreadPool = Executors.newFixedThreadPool(8);
    private CommandManager commandManager;

    public Server(int port, int soTimeout, DataBaseHandler dataBaseHandler, CommandManager commandManager) {
        this.port = port;
        this.soTimeout = soTimeout;
        this.commandManager = commandManager;
        this.dataBaseHandler = dataBaseHandler;
    }
    public void runServer() {
        open();

        while (true) {
            FutureManager.checkAllFutures();
            try {
                Runnable connectionManagerTask = new ConnectionManager(commandManager, connectToClient(), dataBaseHandler);
                fixedThreadPool.submit(connectionManagerTask);
            } catch (SocketTimeoutException e) {
                throw new RuntimeException();
            }
            // Не вызываем stop() здесь, а оставляем его за пределами цикла
        }
    }

    public SocketChannel connectToClient() throws  SocketTimeoutException {
        try {
            serverSocket.socket().setSoTimeout(soTimeout);
            socketChannel = serverSocket.socket().accept().getChannel();
            logger.info("Соединение с клиентом установлено успешно.");
            return socketChannel;
        } catch (SocketTimeoutException exception) {
            logger.info("Превышено время ожидания подключения.");
        } catch (IOException exception) {
            throw new RuntimeException();
            //System.out.println("Произошла ошибка при соединении с клиентом.");
            //logger.info("Произошла ошибка при соединении с клиентом.");
        }
        return null;
    }




    public void open() {
        try {
            logger.info("Запуск сервера...");
            SocketAddress socketAddress = new InetSocketAddress(port);
            serverSocket = ServerSocketChannel.open();
            serverSocket.bind(socketAddress);
            System.out.println("--------------------------------------------------------------------");
            System.out.println("-----------------СЕРВЕР УСПЕШНО ЗАПУЩЕН-----------------------------");
            System.out.println("--------------------------------------------------------------------");

            logger.info("Сервер запущен успешно");
        } catch (IllegalArgumentException exception) {
            logger.info("Порт " + port + "недоступен.");
            throw new RuntimeException();
        } catch (IOException exception) {
            logger.info("Ошибка при использовании порта " + port);
            System.out.println("Введите другой порт");
        }
    }

    public void stop() {
        try {
            logger.info("Завершение работы сервера...");
            fixedThreadPool.shutdown();
            serverSocket.close();
            System.out.println("Работа сервера успешно завершена.");
            logger.info("Работа сервера успешно завершена.");
        } catch (RuntimeException exception) {
            System.out.println("Нельзя завершить работу незапущенного сервера.");
            logger.info("Нельзя завершить работу незапущенного сервера.");
        } catch (IOException exception) {
            System.out.println("Произошла ошибка при завершении работы сервера.");
            logger.info("Произошла ошибка при завершении работы сервера.");
        }
    }

}