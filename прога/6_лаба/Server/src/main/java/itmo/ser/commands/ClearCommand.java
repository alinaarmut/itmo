package itmo.ser.commands;

/**
 * Класс команды clear - очищает коллекцию Movie
 */

import itmo.common.description.managers.Request;
import itmo.common.description.managers.Response;

public class ClearCommand implements Commands{
    @Override
    public Response execute(Request request) {
         collectionManager.ClearCommand();
        return new Response("Коллекция очищена");
    }
}
