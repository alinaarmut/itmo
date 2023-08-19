package itmo.ser.commands;
import itmo.common.description.Movie;
import itmo.common.description.managers.Request;
import itmo.common.description.managers.Response;
/**
 * Класс команды update_id - обновляет значение элемента коллекции, id которого равен заданному
 */
public class UpdateIdCommand implements Commands {

    @Override
    public Response execute(Request request) {
        Movie movie = request.getObject();
        int id = Integer.parseInt(request.getArgs());
        collectionManager.UpdateMovieById(id,movie);

        return new Response("Объект успешно обновлен");
        }
    }






