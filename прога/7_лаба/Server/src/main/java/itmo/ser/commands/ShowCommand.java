package itmo.ser.commands;

import itmo.common.description.managers.Request;
import itmo.common.description.managers.Response;
import itmo.common.description.managers.ResponseStatus;
import itmo.ser.commands.managers.CollectionManager;

/**
 * Класс команды show - показывает полностью элементы коллекции Movie
 */

public class ShowCommand extends Command {
    private final CollectionManager collectionManager;
    public ShowCommand(CollectionManager collectionManager) {
        super("show", "show: вывести все элементы коллекции в строковом представлении");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        if (CollectionManager.movies.isEmpty()) {
            return new Response(ResponseStatus.ERROR, "В коллекции нет элементов");
        }
        return new Response(ResponseStatus.OK,collectionManager.ShowCommand() );

    }
}
