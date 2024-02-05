package itmo.prog.commands;
/**
 * Класс команды remove_by_id
 */
public class RemoveById extends Commands implements command {


    @Override
    public String execute(String args) {
        int movieId = 0;
        try {
            movieId = Integer.parseInt(args);
        } catch (NumberFormatException e) {
           System.out.println( "Ошибка: некорректный формат аргумента - требуется целое число");
        }
        if (!collectionManager.containsMovieById(movieId)) {
            return "Ошибка: фильм с id " + movieId + " не найден в коллекции";
        }
        collectionManager.removeMovieById(movieId);
        if (collectionManager.containsMovieById(movieId)) {
            return "Ошибка: фильм с id " + movieId + " не удален из коллекции";
        } else {
            return "Фильм с id " + movieId + " успешно удален из коллекции";
        }
    }
}



