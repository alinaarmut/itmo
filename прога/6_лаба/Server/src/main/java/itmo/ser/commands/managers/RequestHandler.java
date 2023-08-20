package itmo.ser.commands.managers;

import itmo.common.description.managers.Request;
import itmo.common.description.managers.Response;

public class RequestHandler {
    private CommandManager commandManager;

    public RequestHandler(CommandManager commandManager) {
        this.commandManager = commandManager;
    }
    public Response handle(Request request) {
        return commandManager.execute(request);

    }
}
