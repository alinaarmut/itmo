package itmo.ser.commands;
/**
 * Класс команды remove_by_id - удаляет элемент из коллекции Movie по его id
 */
import itmo.common.description.managers.Request;
import itmo.common.description.managers.Response;



public class RemoveByIdCommand implements Commands{
    @Override
    public Response execute(Request request) {
        int movieId = Integer.parseInt(request.getArgs());
        collectionManager.removeMovieById(movieId);
        return new Response("Удален фильм с id равным" + " " + movieId);
    }
}
