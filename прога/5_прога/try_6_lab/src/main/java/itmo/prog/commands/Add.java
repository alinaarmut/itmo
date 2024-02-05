package itmo.prog.commands;
/**
 * Класс команды add
 */
public class Add extends Commands implements command {

     @Override
     public String execute(String args) {
          collectionManager.putMovie();


          return "Фильм успешно добавлен";
     }
}

