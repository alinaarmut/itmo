package itmo.ser.commands;
import itmo.common.description.managers.Request;
import itmo.common.description.managers.Response;
import itmo.common.description.managers.ResponseStatus;
import itmo.ser.commands.managers.CollectionManager;

public class CountMpCommand extends Command {
    private final CollectionManager collectionManager;

    public CountMpCommand( CollectionManager collectionManager) {
        super("count_mpaarating", "count_mpaarating: выводит количество фильмов с MPAA меньше заданного");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        try {
            int number = Integer.parseInt(request.getArgs().trim());
            return new Response(ResponseStatus.OK, collectionManager.count_mp(number));

        } catch (NumberFormatException exception) {
            return new Response(ResponseStatus.ERROR, "number должен быть типа int");
        }
    }
}
