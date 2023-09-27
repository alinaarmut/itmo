package itmo.ser.commands;
/**
 * Класс команды max_mpaarating - выводит элемент с максимальным полем mpaRating из коллекции Movie
 */

import itmo.common.description.managers.Request;
import itmo.common.description.managers.Response;
import itmo.common.description.managers.ResponseStatus;
import itmo.ser.commands.managers.CollectionManager;

public class MaxMpCommand extends Command{
    private final CollectionManager collectionManager;

    public MaxMpCommand(CollectionManager collectionManager) {
        super("max_mpaarating", "max_mpaarating:выводит элемент с максимальным полем mpaRating из коллекции Movie");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        return new Response(ResponseStatus.OK, collectionManager.mp());
    }
}
