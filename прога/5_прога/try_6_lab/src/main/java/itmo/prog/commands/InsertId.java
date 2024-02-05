package itmo.prog.commands;
/**
 * Класс команды insert_id
 */

public class InsertId extends Commands implements command {
    @Override
    public String execute(String args) {
        try {
            int index = Integer.parseInt(args);
            return collectionManager.insert_id(index);
        } catch (NumberFormatException e) {
            System.out.println("Повторите ввод. Рядом с командой должно быть число");
        }
        return "";
    }

    }



