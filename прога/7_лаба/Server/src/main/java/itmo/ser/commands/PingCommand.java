package itmo.ser.commands;

import itmo.common.description.managers.Request;
import itmo.common.description.managers.Response;
import itmo.common.description.managers.ResponseStatus;

public class PingCommand extends Command {

    public PingCommand() {
        super("ping", "ping : пингануть сервер");
    }

    @Override
    public Response execute(Request request) {
        return new Response(ResponseStatus.OK, "pong");
    }
}
