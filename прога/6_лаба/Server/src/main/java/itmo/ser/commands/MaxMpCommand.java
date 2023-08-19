package itmo.ser.commands;
/**
 * Класс команды max_mpaarating - выводит элемент с максимальным полем mpaRating из коллекции Movie
 */

import itmo.common.description.managers.Request;
import itmo.common.description.managers.Response;

public class MaxMpCommand implements Commands {
    @Override
    public Response execute(Request request) {
        String response = collectionManager.mp();
        return new Response(response);
    }
}
