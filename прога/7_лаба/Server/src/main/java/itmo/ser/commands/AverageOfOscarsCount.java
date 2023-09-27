package itmo.ser.commands;
/**
 * Класс команды AverageOfOscarsCount - выводит среднее значение поля oscarsCount для всех элементов коллекции
 */

import itmo.common.description.managers.Request;
import itmo.common.description.managers.Response;
import itmo.common.description.managers.ResponseStatus;
import itmo.ser.commands.managers.CollectionManager;

public class AverageOfOscarsCount extends Command {
    private CollectionManager collectionManager;
    public AverageOfOscarsCount(CollectionManager collectionManager) {
        super("average_oscars_count", " выводит среднее значение поля oscarsCount для всех элементов коллекции");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        return new Response(ResponseStatus.OK, collectionManager.getAverageOscarsCount());
    }
}

