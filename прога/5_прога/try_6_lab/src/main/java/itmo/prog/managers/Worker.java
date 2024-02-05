package itmo.prog.managers;


import itmo.prog.commands.*;

import java.io.File;
import java.util.NoSuchElementException;
import java.util.Scanner;
public class Worker {
    CommandManager commandManager = new CommandManager();
    CollectionManager collectionManager = new CollectionManager();

    public void cmdAdd() {
        commandManager.register("help", new Help());
        commandManager.register("info", new Info());
        commandManager.register("show", new Show());
        commandManager.register("clear", new Clear());
        commandManager.register("add", new Add());
        commandManager.register("exit", new Exit());
        commandManager.register("save", new Save());
        commandManager.register("insert_id", new InsertId());
        commandManager.register("remove_first", new RemoveFirst());
        commandManager.register("remove_last", new RemoveLast());
        commandManager.register("remove_by_id", new RemoveById());
        commandManager.register("update_id", new UpdateId());
        commandManager.register("average_oscars_count", new AverageOfOscarsCount());
        commandManager.register("max_mpaarating", new MaxMpaarating());
        commandManager.register("execute_script", new ExecuteScript());
        commandManager.register("count_mpaarating", new CountMpaarating());
    }


    public void run(String[] args) {
        String filename;
        Scanner scanner = new Scanner(System.in);

        if (args.length > 0) {
            filename = args[0];

        } else {

            while (true) {
                System.out.print("Введите имя файла: ");
                filename = scanner.nextLine();

                if (filename.isEmpty()) {
                    System.out.println("Имя файла не может быть пустым. Попробуйте снова.");
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

        boolean isFirstCommand = true;


        try {
            while (true) {
                if (!isFirstCommand) {
                    CustomScanner customScanner = new CustomScanner(System.in);

                    String line =customScanner.nextLine();

                    if (line.isEmpty()) {
                        System.out.println("$");
                        continue;
                    }

                    String[] parts = line.split("\\s+", 2);
                    String commandName = parts[0];

                    if (commandManager.isCommandExists(commandName)) {
                        command com = commandManager.getByName(commandName);

                        if (com == null) {
                            System.out.println("Команда не найдена.");
                        } else if (commandManager.hasArgument(commandName) && !(parts.length > 1)) {
                            System.out.println("Ошибка: команда " + commandName + " требует аргумента рядом.");
                        } else {
                            String argument = "";
                            if (parts.length > 1) {
                                argument = parts[1];
                            }
                            String result = com.execute(argument);
                            System.out.println(result);
                        }
                    } else {
                        System.out.println("Команда введена неправильно. Используйте help.");
                    }
                } else {
                  System.out.print("Введите команду: \n");
                }

                isFirstCommand = false;
            }
        } catch (NoSuchElementException e) {
            System.out.println("Произошло закрытие потока. Файл сохранен");
            System.exit(1);
        }
    }

}














