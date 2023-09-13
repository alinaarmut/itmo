package itmo.ser.commands;
/**
 * Класс команды remove_last - удаляет последний элемент из коллекции Movie
 */

import itmo.common.description.managers.Request;
import itmo.common.description.managers.Response;

public class RemoveLastCommand implements Commands{
    @Override
    public Response execute(Request request) {
        String response = collectionManager.RemoveLastElement();
        return new Response(response);
    }
}
