package itmo.prog.commands;

/**
 * Класс команды count_mpaarating
 */
public class CountMpaarating extends Commands implements command {


    @Override
    public String execute(String args) {
        int number = 0;
        try {
            number = Integer.parseInt(args);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: некорректный формат аргумента - требуется целое число");
        }

        return collectionManager.count_mp(number);
    }
}

