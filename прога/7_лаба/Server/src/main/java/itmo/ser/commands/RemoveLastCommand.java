package itmo.ser.commands;
/**
 * Класс команды remove_last - удаляет последний элемент из коллекции Movie
 */

import itmo.common.description.Movie;
import itmo.common.description.err.NoSuchIDExp;
import itmo.common.description.managers.Request;
import itmo.common.description.managers.Response;
import itmo.common.description.managers.ResponseStatus;
import itmo.ser.commands.dateBase.DataBaseHandler;
import itmo.ser.commands.managers.CollectionManager;

public class RemoveLastCommand extends Command {
    private final CollectionManager collectionManager;
    public RemoveLastCommand( CollectionManager collectionManager) {
        super("remove_last", "remove_last: удалить последний элемент из коллекции");
        this.collectionManager = collectionManager;
    }




        @Override
        public Response execute(Request request){
            if (CollectionManager.movies.isEmpty()) {
                return new Response(ResponseStatus.ERROR, "Нечего удалять. Коллекция пустая");
            }

            Movie lastMovie = collectionManager.getLastMovie();
            if (lastMovie != null) {
                int idToDelete = lastMovie.getId();
                if (DataBaseHandler.deleteObjectById(request.getUser(),idToDelete)) {
                    collectionManager.deleteLastMovie();
                    return new Response(ResponseStatus.OK, "Последний элемент успешно удален из коллекции и базы данных");
                } else {
                    return new Response(ResponseStatus.ERROR, "Ошибка при удалении элемента из базы данных");
                }
            } else {
                return new Response(ResponseStatus.ERROR, "Последний элемент не найден");
            }
        }
    }

