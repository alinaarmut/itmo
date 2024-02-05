package itmo.prog.commands;
/**
 * Класс команды remove_last
 */
public class RemoveLast extends Commands  implements command {

    @Override
    public String execute(String args) {

            return collectionManager.RemoveLastElement();
        }

    }


