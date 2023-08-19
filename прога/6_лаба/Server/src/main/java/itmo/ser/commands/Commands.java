package itmo.ser.commands;
import itmo.common.description.managers.Request;
import itmo.common.description.managers.Response;
import itmo.ser.commands.managers.CollectionManager;


public interface Commands {
    CollectionManager collectionManager = new CollectionManager();
    Response execute(Request request);
}
