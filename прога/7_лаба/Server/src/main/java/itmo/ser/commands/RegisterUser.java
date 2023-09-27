package itmo.ser.commands;

import itmo.common.description.managers.Request;
import itmo.common.description.managers.Response;
import itmo.common.description.managers.ResponseStatus;
import itmo.ser.commands.dateBase.DataBaseHandler;


import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;



public class RegisterUser extends Command {
    DataBaseHandler dataBaseHandler;

    public RegisterUser(DataBaseHandler dataBaseHandler) {
        super("register", "register: зарегистрировать пользователя");
        this.dataBaseHandler = dataBaseHandler;
    }

    @Override
    public Response execute(Request request) throws IllegalArgumentException {
        System.out.println("получен юзер: " + request.getUser());
        try {
            dataBaseHandler.registerUser(request.getUser());
        } catch (SQLException e) {
            e.getMessage();
            System.out.println("Невозможно добавить пользователя");
            return new Response(ResponseStatus.LOGIN_FAILED, "Введен невалидный пароль!");
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return new Response(ResponseStatus.OK,"Вы успешно зарегистрированы!");
    }
    }

