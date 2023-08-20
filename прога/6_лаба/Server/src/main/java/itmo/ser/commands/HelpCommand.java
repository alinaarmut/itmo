package itmo.ser.commands;
/**
 * Класс команды help - выводит справку по всем командам
 */

import itmo.common.description.managers.Request;
import itmo.common.description.managers.Response;

public class HelpCommand implements Commands {
    @Override
    public Response execute(Request request) {
        String response = collectionManager.HelpCommand();
        return new Response(response);
    }
}
