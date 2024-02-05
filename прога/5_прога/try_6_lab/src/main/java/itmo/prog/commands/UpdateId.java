package itmo.prog.commands;

/**
 * Класс команды update_id - обновляет значение элемента коллекции, id которого равен заданному
 */
public class UpdateId extends Commands implements command {
    @Override

    public String execute(String args) {
        try {
            int id = Integer.parseInt(args);
            return collectionManager.updateMovieByyId(id);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("");
        }
    }
    }



