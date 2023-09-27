package itmo.ser.commands;
/**
 * Класс команды help - выводит справку по всем командам
 */

import itmo.common.description.managers.Request;
import itmo.common.description.managers.Response;
import itmo.common.description.managers.ResponseStatus;
import itmo.ser.commands.managers.CommandManager;

public class HelpCommand extends Command {
    private final CommandManager commandManager;

    public HelpCommand(CommandManager commandManager) {
        super("help", "help: вывести список всех команд");
        this.commandManager = commandManager;
    }

    public Response execute(Request request)  {
        return new Response(ResponseStatus.OK, """
                       
                Список доступных команд:
                help - показать список команд
                info - вывести информацию о коллекции
                show - показать все элементы коллекции
                add {element} - добавить элемент в коллекцию
                clear - очистить коллекцию
                remove_last - удалить последний элемент из коллекции
                remove_first -  удалить первый элемент из коллекции
                remove_by_id {id} - удалить элемент из коллекции по его id
                execute_script {file_name} - исполнить скрипт из указанного скрипта
                update_id {id} - обновить значение элемента коллекции, id которого равен заданному
                average_oscars_count - вывести среднее значение поля oscarsCount для всех элементов коллекции
                max_mpaarating - вывести объект из коллекции, значение поля mpaaRating которого является максимальным
                count_mpaarating {mpaaRating} - вывести количество элементов, значение поля mpaaRating которых меньше заданного
                exit - выход из программы""");
}
    }