package itmo.ser.commands;
/**
 * Класс команды remove_first - удаляет первый элемент из коллекции Movie
 */

import itmo.common.description.managers.Request;
import itmo.common.description.managers.Response;

public class RemoveFirstCommand implements Commands{

    @Override
    public Response execute(Request request) {
        String response = collectionManager.removeFirstElement();
        return new Response(response);
    }
}
