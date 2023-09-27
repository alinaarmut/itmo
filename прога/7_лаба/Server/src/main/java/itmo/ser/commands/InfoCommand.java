package itmo.ser.commands;
/**
 * Класс команды info - выводит информацию о коллекции
 */

import itmo.common.description.managers.Request;
import itmo.common.description.managers.Response;
import itmo.common.description.managers.ResponseStatus;
import itmo.ser.commands.managers.CollectionManager;

public class InfoCommand extends Command{

    private final CollectionManager collectionManager;
    public InfoCommand(CollectionManager collectionManager) {
        super("info", "вывести информацию о коллекции");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        return  new Response(ResponseStatus.OK, collectionManager.InfoCommand());
    }
}


