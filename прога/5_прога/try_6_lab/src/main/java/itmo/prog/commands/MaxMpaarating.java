package itmo.prog.commands;
/**
 * Класс команды max_mpaarating
 */

public class MaxMpaarating extends Commands implements command {

    @Override
    public String execute(String args) {
        return collectionManager.mp();
    }
}
