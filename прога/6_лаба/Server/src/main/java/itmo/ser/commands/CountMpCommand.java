package itmo.ser.commands;
import itmo.common.description.managers.Request;
import itmo.common.description.managers.Response;

public class CountMpCommand implements Commands{
    @Override
    public Response execute(Request request) {
        int number  = Integer.parseInt((request.getArgs()));
        return new Response(collectionManager.count_mp(number));
    }
}
