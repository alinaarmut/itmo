package itmo.prog.commands;
/**
 * Класс команды exit
 */
public class Exit extends Commands implements command {

    @Override
    public String execute(String args) {
        return collectionManager.ExitCommand();
    }
}
