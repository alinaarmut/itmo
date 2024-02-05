package itmo.prog.commands;
/**
 * Класс команды show
 */
public class Show extends Commands  implements command {
    @Override
    public String execute(String args) {

        return collectionManager.ShowCommand();
    }
}
