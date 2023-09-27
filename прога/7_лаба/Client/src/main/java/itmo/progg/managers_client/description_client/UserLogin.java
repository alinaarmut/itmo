package itmo.progg.managers_client.description_client;

import itmo.common.description.managers.User;

import java.util.Objects;

public class UserLogin extends Execution<User> {
    @Override
    public User build() {
        return new User(
                askLogin(),
                askPassword()
        );
    }

    public String askIfLogin(){
        String answer;
        for(;;) {
            System.out.println("Вы зарегистрированы? (yes/no)\n");
            answer = customScanner.nextLine().trim();
            if (answer.equalsIgnoreCase("yes")) return "login";
            if (answer.equalsIgnoreCase("no")) return "register";
            else {
                System.out.println("Ответ может быть только \"yes\" или \"no\"\n");
            }
        }
    }



    private String askLogin(){
        String login;
        while (true){
            System.out.println("Введите ваш логин");
            login = customScanner.nextLine().trim();
            if (login.isEmpty()){
                System.out.println("Логин не может бытьΩßß пустым");
            }
            else{
                return login;
            }
        }
    }

    private String askPassword(){
        String pass;
        while (true){
            System.out.println("Введите пароль");
            pass = (Objects.isNull(System.console()))
                    ? customScanner.nextLine().trim()
                    : new String(System.console().readPassword());
            if (pass.isEmpty()){
                System.out.println("Пароль не может быть пустым");
            }
            else{
                return pass;
            }
        }
    }

}
