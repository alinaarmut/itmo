package itmo.ser.commands;

import itmo.common.description.managers.Request;
import itmo.common.description.managers.Response;

public class ExitCommand implements Commands{
    @Override
    public Response execute(Request request) {
        return new Response("EXIT: Завершение клиентского приложения");
    }
}
