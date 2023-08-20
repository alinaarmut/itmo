package itmo.ser.commands;

import itmo.common.description.managers.Request;
import itmo.common.description.managers.Response;


public class ExecuteScriptCommand implements Commands{
    @Override
    public Response execute(Request request) {
        String filename = request.getArgs();
        String result = collectionManager.execute_script(filename);
        if (result.startsWith("ERROR")) {
            return new Response(result);
        } else {
            return new Response(result);
        }
    }
    }


