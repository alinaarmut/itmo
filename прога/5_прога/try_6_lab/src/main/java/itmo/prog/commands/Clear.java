package itmo.prog.commands;
/**
 * Класс команды clear
 */
public class Clear extends Commands implements command {



    @Override
    public String execute(String args) {
        collectionManager.ClearCommand();
        return "Коллекция успешно очищена";
    }
}
