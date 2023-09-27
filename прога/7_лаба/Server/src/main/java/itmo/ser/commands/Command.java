package itmo.ser.commands;

import itmo.common.description.managers.Request;
import itmo.common.description.managers.Response;
import itmo.ser.commands.managers.CollectionManager;

public abstract class Command {
    private final String name;
    private final String info;


    public Command(String name, String info) {
        this.name = name;
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }

    public abstract Response execute(Request request) ;

}
