package itmo.prog.commands;

/**
 * Класс команды info
 */
public class Info extends Commands  {


    @Override
    public String execute(String args) {
            return collectionManager.InfoCommand();
    }
}
