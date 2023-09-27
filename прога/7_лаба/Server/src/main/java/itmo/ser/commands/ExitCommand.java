package itmo.ser.commands;

import itmo.common.description.managers.Request;
import itmo.common.description.managers.Response;
import itmo.common.description.managers.ResponseStatus;

public class ExitCommand extends Command{

    public ExitCommand() {
        super("exit", "exit: завершить программу (без сохранения в файл)");
    }

    @Override
    public Response execute(Request request) {
        return new Response(ResponseStatus.EXIT);
    }
}
