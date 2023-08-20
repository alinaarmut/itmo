package itmo.ser.commands;
/**
 * Класс команды add - добавляет элемент в коллекцию Movie
 */

import itmo.common.description.Movie;
import itmo.common.description.managers.Request;
import itmo.common.description.managers.Response;

public class AddCommand implements Commands{

    @Override
    public Response execute(Request request) {
        Movie movie = request.getObject();
        collectionManager.AddCommand( movie);
        return new Response("Элемент успешно добавлен");
    }
}
