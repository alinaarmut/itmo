package itmo.ser.commands;
/**
 * Класс команды info - выводит информацию о коллекции
 */

import itmo.common.description.managers.Request;
import itmo.common.description.managers.Response;

public class InfoCommand implements Commands{

        @Override
        public Response execute(Request request) {
            String response = collectionManager.InfoCommand();

            return new Response(response);

        }
    }


