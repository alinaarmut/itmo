package itmo.ser.commands;
/**
 * Класс команды add - добавляет элемент в коллекцию Movie
 */

import itmo.common.description.Movie;
import itmo.common.description.managers.Request;
import itmo.common.description.managers.Response;
import itmo.common.description.managers.ResponseStatus;
import itmo.ser.commands.dateBase.DataBaseHandler;
import itmo.ser.commands.managers.CollectionManager;

import java.util.Objects;
import java.util.logging.Logger;

public class AddCommand extends Command {
    Logger logger = Logger.getLogger(AddCommand.class.getName());
    private final CollectionManager collectionManager;

    public AddCommand(CollectionManager collectionManager) {
        super("add", "add {element}: добавить новый элемент в коллекцию");
        this.collectionManager = collectionManager;
    }


    @Override
    public Response execute(Request request) {

        if (Objects.isNull(request.getMovie())) {
            return new Response(ResponseStatus.ASK_OBJECT, "Требуется объект");
        }
        else {
            logger.info("получен объект");
            int id = DataBaseHandler.addObject(request.getMovie(), request.getUser());
            if (id == -1) return new Response(ResponseStatus.ERROR, "Элемент не удалось добавить");
            request.getMovie().setId(id);
            request.getMovie().setOwner(request.getUser().getUsername());
            collectionManager.add(request.getMovie());
            return new Response(ResponseStatus.OK, "Элемент добавлен в коллекцию");
        }
    }
}
