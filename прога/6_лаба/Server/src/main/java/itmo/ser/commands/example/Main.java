package itmo.ser.commands.example;
import itmo.ser.commands.managers.CommandManager;
import itmo.ser.commands.managers.Converter;
import itmo.ser.commands.managers.RequestHandler;
import itmo.ser.commands.managers.Server;
//import managers.*;
import java.io.File;
import java.util.Scanner;
import java.util.logging.Logger;


public class Main {
    private static final Logger Mainlogger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        String filename;
        Scanner scanner = new Scanner(System.in);

        if (args.length > 0) {
            filename = args[0];

        } else {

            while (true) {
                System.out.print("Введите имя файла: ");
                filename = scanner.nextLine();

                if (filename.isEmpty()) {
                    Mainlogger.info("Имя файла не может быть пустым. Попробуйте снова.");
                    continue;
                }

                File file = new File(filename);
                if (file.exists()) {
                    break;
                } else {
                    Converter converter = new Converter();
                    converter.createEmptyCollectionFile(filename);
                    break;
                }
            }
        }
            Converter converter = new Converter();
            converter.readCollectionFromFile(filename);

            int PORT = 8812;
            CommandManager commandManager = new CommandManager();
            RequestHandler requestHandler = new RequestHandler(commandManager);
            Server server = new Server(PORT, requestHandler);

            server.run();


    }
}