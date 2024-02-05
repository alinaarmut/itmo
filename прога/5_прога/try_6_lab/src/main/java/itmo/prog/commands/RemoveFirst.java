package itmo.prog.commands;
/**
 * Класс команды remove_first
 */
public class RemoveFirst extends Commands implements command {
    @Override
    public String execute(String args) {
        return collectionManager.RemoveFirstElement();
    }
}
