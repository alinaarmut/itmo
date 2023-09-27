package itmo.ser.commands.example;

import itmo.ser.commands.*;
import itmo.ser.commands.dateBase.DataBaseHandler;


import itmo.ser.commands.managers.*;

import java.util.List;
import java.util.logging.Logger;


public class Main {
    public static final int connection_timeout = 60 * 500;
    private static final Logger Mainlogger = Logger.getLogger(Main.class.getName());
    public static final String HASHING_ALGORITHM = "SHA-224";
    static int PORT = 6023;


    public static void main(String[] args) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Драйвер не найден!!!");
            e.printStackTrace();
            System.exit(-1);
        }
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        CommandManager commandManager = new CommandManager(dataBaseHandler);
        CollectionManager collectionManager = new CollectionManager();

        commandManager.addCommand(List.of(
                new HelpCommand(commandManager),
                new ShowCommand(collectionManager),
                new ExecuteScriptCommand(),
                new RegisterUser(dataBaseHandler),
                new ExitCommand(),
                new InfoCommand(collectionManager),
                new LoginUser(dataBaseHandler),
                new ClearCommand(collectionManager),
                new AddCommand(collectionManager),
                new RemoveByIdCommand(collectionManager),
                new RemoveFirstCommand(collectionManager),
                new RemoveLastCommand(collectionManager),
                new UpdateIdCommand(collectionManager),
                new AverageOfOscarsCount(collectionManager),
                new MaxMpCommand(collectionManager),
                new CountMpCommand(collectionManager),
                new PingCommand()

        ));
        Server server = new Server(PORT, connection_timeout, dataBaseHandler, commandManager);
        server.runServer();


    }


}
