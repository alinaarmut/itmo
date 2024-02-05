package itmo.prog.commands;
/**
 * Класс команды execute_script
 */
public class ExecuteScript extends Commands implements command {

    @Override
    public String execute(String args) {

        String filename = args;
        while (true) {
            try {
        collectionManager.execute_script(filename);
               return "Выполнение скрипта прошло успешно";

            } catch (Exception e) {
                System.out.println("Ошибка выполнения скрипта");
                return "Введите корректно команды или данные внутри файла"  ;
            }
        }

    }
    }


