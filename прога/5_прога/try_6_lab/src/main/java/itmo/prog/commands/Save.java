package itmo.prog.commands;
/**
 * Класс команды save
 */

public class Save extends Commands implements command {


    @Override
    public String execute(String args) {
          collectionManager.SaveCommand();
        return "Коллекция успешно сохранена в файл";
    }
}
