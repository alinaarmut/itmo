package itmo.prog.commands;
/**
 * Класс команды average_of_oscars_count
 */
public class AverageOfOscarsCount extends Commands implements  command{




    @Override
    public String execute(String args) {
        return collectionManager.getAverageOscarsCount();
    }
}


