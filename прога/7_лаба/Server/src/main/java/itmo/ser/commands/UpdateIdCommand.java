package itmo.ser.commands;

import itmo.common.description.err.NoSuchIDExp;
import itmo.common.description.managers.Request;
import itmo.common.description.managers.Response;
import itmo.common.description.managers.ResponseStatus;
import itmo.ser.commands.managers.CollectionManager;
import itmo.ser.commands.dateBase.DataBaseHandler;

import java.util.Objects;

/**
 * Класс команды update_id - обновляет значение элемента коллекции, id которого равен заданному
 */
public class UpdateIdCommand extends Command {


    private final CollectionManager collectionManager;

    public UpdateIdCommand(CollectionManager collectionManager) {
        super("update_id", "update id {element}: обновить значение элемента коллекции, id которого равен заданному");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        if (CollectionManager.movies.isEmpty()) {
            return new Response(ResponseStatus.ERROR, "Не удается выполнить команду. Коллекция пуста");
        }
        try {
           Integer id = Integer.parseInt(request.getArgs());
            if (!collectionManager.checkExist(id)) throw new NoSuchIDExp();
            if (CollectionManager.checkUsersId(id, request.getUser().getUsername())) {
                if (Objects.isNull(request.getMovie())){
                    return new Response(ResponseStatus.ASK_OBJECT, "Для команды " + this.getName() + " требуется объект");
                }
                if (DataBaseHandler.update(id, request.getMovie(), request.getUser())){
                    collectionManager.update(request.getMovie(), id);
                    return new Response(ResponseStatus.OK, "Элемент с id " + id + " обновлён");
                }
                return new Response(ResponseStatus.ERROR, "Элемент с id " + id + " не обновлён");
            }
            return new Response(ResponseStatus.ERROR, "У вас нет прав на этот объект");
        } catch (NumberFormatException e) {
            return new Response(ResponseStatus.WRONG_ARGUMENTS, "Команда не выполнена. Вы ввели некорректный аргумент");
        } catch (NoSuchIDExp e) {
            return new Response(ResponseStatus.ERROR, "Элемента с таким id нет в коллекции");
        }
    }
}







