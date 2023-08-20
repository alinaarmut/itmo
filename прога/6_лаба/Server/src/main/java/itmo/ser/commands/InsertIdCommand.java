package itmo.ser.commands;

import itmo.common.description.Movie;
import itmo.common.description.managers.Request;
import itmo.common.description.managers.Response;
/**
 * Класс команды insert_id - добавить новый элемент с заданным ключом id
 */
public class InsertIdCommand implements Commands {
    @Override
    public Response execute(Request request) {
        Movie movie = request.getObject();
        int index = Integer.parseInt(request.getArgs());
        collectionManager.InsertIdCommand(index,movie);
        return new Response("Объект успешно обновлен");
    }

}
