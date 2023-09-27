package itmo.ser.commands.managers;

import itmo.common.description.managers.Request;
import itmo.common.description.managers.Response;
import itmo.common.description.managers.ResponseStatus;
import itmo.ser.commands.exceptions.CommandRuntimeException;
import itmo.ser.commands.exceptions.*;
import itmo.ser.commands.exceptions.IllegalArgumentsException;
import itmo.ser.commands.exceptions.NoSuchCommandException;

import java.io.ObjectOutputStream;
import java.util.concurrent.Callable;


public class RequestHandler implements Callable<ConnectionManagerPool> {
    private CommandManager commandManager;
    private Request request;
    private ObjectOutputStream objectOutputStream;


    public RequestHandler(CommandManager commandManager, Request request, ObjectOutputStream objectOutputStream) {
        this.commandManager = commandManager;
        this.request = request;
        this.objectOutputStream = objectOutputStream;
    }

    public ObjectOutputStream getObjectOutputStream() {
        return objectOutputStream;
    }

    public void setObjectOutputStream(ObjectOutputStream objectOutputStream) {
        this.objectOutputStream = objectOutputStream;
    }

    @Override
    public ConnectionManagerPool call() throws CommandRuntimeException {
        try {
            return new ConnectionManagerPool(commandManager.execute(request), objectOutputStream);
        } catch (IllegalArgumentsException e) {
            return new ConnectionManagerPool(new Response(ResponseStatus.WRONG_ARGUMENTS,
                    "Неверное использование аргументов команды"), objectOutputStream);
        } catch (NoSuchCommandException e) {
            return new ConnectionManagerPool(new Response(ResponseStatus.ERROR, "Такой команды нет в списке"), objectOutputStream);
        } catch (ExitObligedException e) {
            return new ConnectionManagerPool(new Response(ResponseStatus.EXIT), objectOutputStream);
        }
    }
}