package itmo.progg.managers_client;

import itmo.common.description.Movie;
import itmo.common.description.err.ExitExp;
import itmo.common.description.managers.ResponseStatus;
import itmo.common.description.managers.User;
import itmo.progg.managers_client.description_client.MovieBuilder;
import itmo.common.description.managers.Request;
import itmo.common.description.managers.Response;
import itmo.progg.managers_client.description_client.UserLogin;
import itmo.progg.managers_client.utility.Console;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.logging.Logger;


public class Worker extends Exception{
    private HashSet<String> executedScripts = new HashSet<>();
    private Client client;
    private User user = null;
    private CustomScanner customScanner;
    private final Console console;
    private static final Logger logger = Logger.getLogger(Worker.class.getName());
    public Worker(Client client, CustomScanner customScanner, Console console) {
        this.client = client;
        this.customScanner = customScanner;
        this.console = console;
    }

    public void run() {
        CustomScanner customScanner = new CustomScanner(System.in);
        while (true) {
            try {
                if (Objects.isNull(user)) {
                    Response response = null;
                   String command;
                    do {
                        UserLogin userLogin = new UserLogin();
                        command = userLogin.askIfLogin();
                        user = new UserLogin().build();
                        response = client.sendAndAskResponse(new Request(command, "", user));
                        logger.info("Received response: " + response.toString());
                        printResponse(response);
                    } while (response.getResponseStatus() != ResponseStatus.OK);
                    System.out.println("Вы успешно зашли в аккаунт. \"Для получения справки о доступных командах введите help\\n\" " );
                }
               // if (!customScanner.hasNext()) throw new ExitObligedException();
                String[] userCommand = (customScanner.nextLine().trim() + " ").split(" ", 2); // прибавляем пробел, чтобы split выдал два элемента в массиве
                Response response = client.sendAndAskResponse(new Request(userCommand[0].trim(), userCommand[1].trim(), user));
                this.printResponse(response);
                switch (response.getResponseStatus()) {
                    case ASK_OBJECT -> {
                        Movie movieBuilder = new MovieBuilder( customScanner).build();
                       Response newResponse = client.sendAndAskResponse(new Request(
                                userCommand[0].trim(),
                                userCommand[1].trim(),
                                user, movieBuilder));
                        if (newResponse.getResponseStatus() != ResponseStatus.OK) {
                            System.out.println(newResponse.getResponse());
                        } else {
                            this.printResponse(newResponse);
                        }
                    }
                    case EXIT -> throw new ExitExp();
                    case EXECUTE_SCRIPT -> {
                      Console.setFileMode(true);
                      this.fileExecution(response.getResponse());
                      Console.setFileMode(false);
                    }
                    case LOGIN_FAILED -> {
                        System.out.println("Ошибка с вашим аккаунтом. Зайдите в него снова");
                        this.user = null;
                    }
                    default -> {
                    }
                }
            } catch (RuntimeException e) {
               // System.out.println("До свидания!");
                System.out.println("Произошла ошибка: " + e.getMessage());
                e.printStackTrace();
              //  return;
            } catch (ExitExp e) {
                System.out.println("Завершение программы");
                return;
            }
        }
    }

    private void printResponse(Response response) {
        if (response == null) {
            System.out.println("Получен пустой ответ от сервера.");
            return;
        }
        switch (response.getResponseStatus()){
            case OK -> {
                if ((Objects.isNull(response.getMovie()))) {
                    System.out.println(response.getResponse());
                } else {
                    System.out.println(response.getResponse() + "\n" + response.getMovie().toString());
                }
            }
            case ERROR -> System.out.println(response.getResponse());
            case WRONG_ARGUMENTS -> System.out.println("Неверное использование команды!");
            default -> {}
        }
    }
    private void fileExecution(String args) throws ExitExp{

        args = args.trim();
        try {
            ExecuteFileManager.pushFile(args);
            for (String line = ExecuteFileManager.readLine();
                 line != null;
                 line = ExecuteFileManager.readLine()) {
                String[] userCommand = (line + " ").split(" ", 2);
                userCommand[1] = userCommand[1].trim();
                if (userCommand[0].isBlank()) return;
                if (userCommand[0].equals("execute_script")){
                    if(ExecuteFileManager.fileRepeat(userCommand[1])){
                        System.out.println("Пресечена попытка рекурсивного вызова скрипта");
                        continue;
                    }
                }
                Response response = client.sendAndAskResponse(new Request(userCommand[0].trim(), userCommand[1].trim(), user));
                this.printResponse(response);
                switch (response.getResponseStatus()){
                    case ASK_OBJECT -> {
                        Movie movie;
                        movie = new MovieBuilder(customScanner).build();
                        Response newResponse = client.sendAndAskResponse(
                                new Request(
                                        userCommand[0].trim(),
                                        userCommand[1].trim(),
                                        user,
                                        movie));
                        if (newResponse.getResponseStatus() != ResponseStatus.OK){
                            System.out.println(newResponse.getResponse());
                        }
                        else {
                            this.printResponse(newResponse);
                        }
                    }
                    case EXIT -> throw new ExitExp();
                    case EXECUTE_SCRIPT -> {
                        this.fileExecution(response.getResponse());
                        ExecuteFileManager.popRecursion();
                    }
                    case LOGIN_FAILED -> {
                        System.out.println("Ошибка с вашим аккаунтом. Зайдите в него снова");
                        this.user = null;
                    }
                    default -> {}
                }
            }
            executedScripts.remove(new File(args).getAbsolutePath());
            ExecuteFileManager.popFile();
        } catch (FileNotFoundException fileNotFoundException){
            System.out.println("Такого файла не существует");
        } catch (IOException e) {
            System.out.println("Ошибка ввода вывода");
        }
    }

}