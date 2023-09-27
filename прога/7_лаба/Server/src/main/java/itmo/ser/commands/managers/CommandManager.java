package itmo.ser.commands.managers;

import itmo.common.description.managers.Request;
import itmo.common.description.managers.Response;
import itmo.ser.commands.*;
import itmo.ser.commands.dateBase.DataBaseHandler;
import itmo.ser.commands.exceptions.ExitObligedException;
import itmo.ser.commands.exceptions.IllegalArgumentsException;
import itmo.ser.commands.exceptions.NoSuchCommandException;


import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;


public class CommandManager {

    private final DataBaseHandler dataBaseHandler;

    protected final HashMap<String, Command> commands = new HashMap<>();

    public CommandManager(DataBaseHandler dataBaseHandler) {
        this.dataBaseHandler = dataBaseHandler;
    }

    public void addCommand(Collection<Command> commands) {
        this.commands.putAll(commands.stream()
                .collect(Collectors.toMap(Command::getName, с -> с)));
    }

    public Collection<Command> getCommands() {
        return commands.values();
    }


    public Response execute(Request request) throws IllegalArgumentsException, NumberFormatException, ExitObligedException, NoSuchCommandException {
        Command command = commands.get(request.getCommandName());
        if (command == null) {
            System.out.println("Команда отсутствует");
            throw new NoSuchCommandException();
        }
        Response response = command.execute(request);
        return response;
    }

}

