package itmo.prog.managers;
import itmo.prog.commands.Commands;

import java.util.HashMap;

/**
 * Класс CommandManager - вызывает команды, хранит список команд и предоставлять интерфейс для их вызова
 */

public class CommandManager  {
    protected final HashMap<String, Commands> commands = new HashMap<>();

    public HashMap<String, Commands> getCommands() {
        return commands;
    }

    public Commands getByName(String commandName){
       return this.commands.get(commandName);
    }

    public void register(String commandName, Commands command) {
        commands.put(commandName, command);
    }
    public boolean isCommandExists(String command) {
        return commands.containsKey(command);
    }
    public boolean hasArgument(String commandName) {
        return switch (commandName) {
            case "remove_by_id", "insert_id", "update_id", "execute_script", "count_mpaarating"  -> true;
            case "help", "clear", "show", "info", "remove_last", "remove_first", "average_oscars_count", "save", "max_mpaarating", "add", "exit"->
                    false;
            default -> throw new IllegalArgumentException("Неизвестная команда: " + commandName);
        };
    }


}
