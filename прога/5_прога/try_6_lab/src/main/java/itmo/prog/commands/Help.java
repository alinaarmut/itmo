package itmo.prog.commands;

/**
 * Класс команды help
 */
public class Help extends Commands implements command {




    @Override
    public String execute(String args) {
        return  collectionManager.HelpCommand();
    }

    }

