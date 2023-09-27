package itmo.ser.commands;

import itmo.common.description.managers.Request;
import itmo.common.description.managers.Response;
import itmo.common.description.managers.ResponseStatus;
import itmo.ser.commands.dateBase.DataBaseHandler;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class LoginUser extends Command {
    DataBaseHandler dataBaseHandler;

    public LoginUser(DataBaseHandler dataBaseHandler) {
        super("login", "login: войти");
        this.dataBaseHandler = dataBaseHandler;
    }

    @Override
    public Response execute(Request request) throws IllegalArgumentException {
        System.out.println("получен юзер: " + request.getUser());
        try {
            dataBaseHandler.loginUser(request.getUser());
            System.out.println("юзер успешно авторизовался");
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return new Response(ResponseStatus.OK,"Вы успешно авторизовались");
    }

}
