package itmo.ser.commands;

import itmo.common.description.managers.Request;
import itmo.common.description.managers.Response;
import itmo.common.description.managers.ResponseStatus;


public class ExecuteScriptCommand extends Command {


    public ExecuteScriptCommand() {
        super("execute_script", "execute_script: выполнить скрипт");
    }

    @Override
    public Response execute(Request request) {
        return new Response(ResponseStatus.EXECUTE_SCRIPT, request.getArgs());
    }
}


