package itmo.prog.commands;
import itmo.prog.managers.CollectionManager;

/**
 * Интерфейс command
 */

public interface command {
    CollectionManager collectionManager = new CollectionManager();
    String execute(String args);

    }



