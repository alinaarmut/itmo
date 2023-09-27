package itmo.ser.commands;

/**
 * Класс команды clear - очищает коллекцию Movie
 */

import itmo.common.description.managers.Request;
import itmo.common.description.managers.Response;
import itmo.common.description.managers.ResponseStatus;
import itmo.ser.commands.dateBase.DataBaseHandler;
import itmo.ser.commands.managers.CollectionManager;

public class ClearCommand extends Command {
    private final CollectionManager collectionManager;
    public ClearCommand(CollectionManager collectionManager) {
        super("clear", "clear: Очистить коллекцию");
        this.collectionManager = collectionManager;
    }


    @Override
    public Response execute(Request request) {
        if (CollectionManager.movies.isEmpty()) {
            return new Response(ResponseStatus.ERROR, "Не удается выполнить команду. Коллекция пуста");
        }
        if (DataBaseHandler.clear(request.getUser())) {
            CollectionManager.ClearCommand();
            return new Response(ResponseStatus.OK, "Коллекция успешно очищена");
        }
        return new Response(ResponseStatus.ERROR, "Коллекция не очищена");
    }
    }


