package itmo.ser.commands.managers;

import itmo.common.description.Movie;
import itmo.common.description.managers.Request;
import itmo.common.description.managers.Response;
import itmo.ser.commands.*;


import java.util.HashMap;


public class CommandManager {

    protected final HashMap<String, Commands> commands = new HashMap<>();


    public HashMap<String, Commands> getCommands() {
        return commands;
    }

    public Commands getByName(String commandName) {
        return this.commands.get(commandName);
    }

    public void register(String commandName, Commands command) {
        commands.put(commandName, command);
    }

    public boolean isCommandExists(String command) {
        return commands.containsKey(command);
    }

    public void cmdAdd() {
        register("info", new InfoCommand());
        register("help", new HelpCommand());
        register("clear", new ClearCommand());
        register("add", new AddCommand());
        register("show", new ShowCommand());
        register("remove_first",new RemoveFirstCommand());
        register("remove_last",new RemoveLastCommand());
        register("exit",new ExitCommand());
        register("insert_id", new InsertIdCommand());
        register("remove_by_id", new RemoveByIdCommand());
        register("update_id", new UpdateIdCommand());
        register("average_oscars_count", new AverageOfOscarsCount());
        register("max_mpaarating", new MaxMpCommand());
        register("execute_script", new ExecuteScriptCommand());
        register("count_mpaarating", new CountMpCommand());
    }

        public boolean hasArgument(String commandName) {
        return switch (commandName) {
            case "remove_by_id", "insert_id", "update_id", "execute_script", "count_mpaarating"  -> true;
            case "help", "clear", "show", "info", "remove_last", "remove_first", "average_oscars_count", "save", "max_mpaarating", "add", "exit"->
                    false;
           default -> throw new IllegalArgumentException("Неизвестная команда: " + commandName);
        };
    }

    public Response execute(Request request) {
        cmdAdd();
        Commands command = commands.get(request.getCommandName());

        if (command != null) {
            if (hasArgument(request.getCommandName())) {
                String args = request.getArgs();
                Movie movie = request.getObject();
                return command.execute(new Request(request.getCommandName(), args, movie));
            } else if (request.getObject() != null) {
                return command.execute(request);
            } else {
                return command.execute(new Request(request.getCommandName()));
            }
        } else {
            System.out.println("Команда не найдена.");
            return new Response("Команда не найдена");
        }
    }

}
