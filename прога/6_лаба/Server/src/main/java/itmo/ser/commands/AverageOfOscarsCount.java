package itmo.ser.commands;
/**
 * Класс команды AverageOfOscarsCount - выводит среднее значение поля oscarsCount для всех элементов коллекции
 */

import itmo.common.description.managers.Request;
import itmo.common.description.managers.Response;

public class AverageOfOscarsCount implements Commands{
    @Override
    public Response execute(Request request) {
        String response = collectionManager.getAverageOscarsCount();
        return new Response(response);
    }
}
