package itmo.ser.commands;

import itmo.common.description.managers.Request;
import itmo.common.description.managers.Response;
/**
 * Класс команды show - показывает полностью элементы коллекции Movie
 */

public class ShowCommand implements Commands{

    @Override
    public Response execute(Request request) {

        return new Response(collectionManager.ShowCommand());

    }
}
