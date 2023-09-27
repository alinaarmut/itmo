package itmo.ser.commands;
/**
 * Класс команды remove_first - удаляет первый элемент из коллекции Movie
 */

import itmo.common.description.Movie;
import itmo.common.description.managers.Request;
import itmo.common.description.managers.Response;
import itmo.common.description.managers.ResponseStatus;
import itmo.ser.commands.dateBase.DataBaseHandler;
import itmo.ser.commands.managers.CollectionManager;

import static itmo.ser.commands.managers.CollectionManager.movies;

public class RemoveFirstCommand extends Command {
    private final CollectionManager collectionManager;

    public RemoveFirstCommand(CollectionManager collectionManager) {
        super("remove_first", "remove_first: удалить первый элемент из коллекции");
        this.collectionManager = collectionManager;
    }


    @Override
    public Response execute(Request request) {
        if (movies.isEmpty()) {
            return new Response(ResponseStatus.ERROR, "Нечего удалять. Коллекция пустая");
        }
        Movie firstMovie = collectionManager.getFirstMovie();
        if (firstMovie != null) {
            int idToDelete = firstMovie.getId();

            if (DataBaseHandler.deleteObjectById(request.getUser(), idToDelete)) {
                collectionManager.deleteFirstMovie();
                return new Response(ResponseStatus.OK, "Первый элемент успешно удален из коллекции и базы данных");
            } else {
                return new Response(ResponseStatus.ERROR, "Ошибка при удалении элемента из базы данных");
            }
        } else {
            return new Response(ResponseStatus.ERROR, "Первый элемент не найден");
        }
    }
}

