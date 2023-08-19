package itmo.progg.managers_client;

import itmo.common.description.Movie;
import itmo.progg.managers_client.description_client.MovieBuilder;
import itmo.ser.commands.managers.CollectionManager;
import itmo.common.description.managers.Request;
import itmo.common.description.managers.Response;

import java.io.EOFException;
import java.util.ArrayList;
import java.util.logging.Logger;


public class Worker extends Exception{
    private static final Logger Workerlogger = Logger.getLogger(Worker.class.getName());
    private Client client;
    public Worker(Client client) {
        this.client = client;


    }
    public Worker(ArrayList<Movie> movies) {
        CollectionManager.movies = movies;

    }

    public void run() throws EOFException {
        CustomScanner customScanner = new CustomScanner(System.in);

        try {
            client.connectToServer();

            while (true) {
                System.out.print("Введите команду:\n");
                String line = customScanner.nextLine();
                String[] parts = line.split("\\s+", 2);
                String commandName = parts[0];

                String args = parts.length > 1 ? parts[1] : "";
                if (commandName.equals("exit")) {
                    Response response = client.sendAndAskResponse(new Request(commandName, args));
                    this.printResponse(response);
                    break;
                } else if (commandName.equals("add")) {
                    MovieBuilder movie = new MovieBuilder(customScanner);
                    Response addResponse = client.sendAndAskResponse(
                            new Request(
                                    commandName,
                                    args,
                                    movie.build()));
                    this.printResponse(addResponse);

                    if (addResponse.getResponse().equals("Элемент успешно добавлен")) {
                        System.out.println("Все прошло успешно");
                    }
                } else if (commandName.equals("update_id") || commandName.equals( "insert_id")) {

                    MovieBuilder movie = new MovieBuilder(customScanner );
                    Movie updatedMovie = movie.build();

                        Response newResponse = client.sendAndAskResponse(
                                new Request(
                                        commandName,
                                        args,
                                        updatedMovie));
                    this.printResponse(newResponse);
                    if (newResponse.getResponse().equals("Объект успешно обновлен")) {
                        System.out.println("Все прошло успешно");

                    } else {
                        System.out.println(newResponse.getResponse());
                    }
                } else if (commandName.equals("execute_script")) {
                    if (!args.isEmpty()) {
                        try {
                            Response response = client.sendAndAskResponse(new Request(commandName, args));
                            printResponse(response);


                            if (response.getResponse().startsWith("ERROR")) {
                                System.out.println("Ошибка при выполнении команды: " + response.getResponse());
                                Workerlogger.info("Ошибка при выполнении команды: " + response.getResponse());
                            }
                            if (response.getResponse().contains("EXIT")) {
                                break;
                            }


                        } catch (Exception e) {
                            System.out.println("Произошла ошибка. Введите корректное имя файла или проверьте данные в файле. И попробуйте еще раз");
                            Workerlogger.info("Произошла ошибка. Введите корректное имя файла или проверьте данные в файле. И попробуйте еще раз");
                        }
                    }

            } else {
                    try {
                        Response response = client.sendAndAskResponse(new Request(commandName, args));
                        this.printResponse(response);
                    } catch (Exception e) {
                        System.out.println("Ошибка при выполнении команды: " + e.getMessage());
                        Workerlogger.info("Ошибка при выполнении команды: " + e.getMessage());

                    }
                }

            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } finally {
            client.disconnectFromServer();
        }
    }

    private void printResponse(Response response) {
        if (response.getCollection() == null) {
            System.out.println(response.getResponse());
        } else {
            System.out.println(response.getResponse() + "\n" + response.getCollection().toString());
        }
    }

}