package itmo.ser.commands.managers;

import itmo.ser.commands.Commands;
//import description.*;
import itmo.common.description.*;
import itmo.common.description.managers.Request;
import itmo.common.description.managers.Response;

import java.io.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

public class CollectionManager {
    public static ArrayList<Movie> movies;

    static {
        movies = new ArrayList<>();

    }
    private LocalDateTime lastSaveTime;

    public String InfoCommand() {
        long nonNullCount = movies.stream()
                .filter(Objects::nonNull)
                .count();

        return "Тип коллекции: " + movies.getClass().getSimpleName() + "\n" +
                "Количество элементов: " + nonNullCount + "\n" +
                "Дата инициализации: " + new Date();
    }
    public String HelpCommand() {
        return """
                          
                Список доступных команд:
                help - показать список команд
                info - вывести информацию о коллекции
                show - показать все элементы коллекции
                add {element} - добавить элемент в коллекцию
                clear - очистить коллекцию
                remove_last - удалить последний элемент из коллекции
                remove_first -  удалить первый элемент из коллекции
                remove_by_id {id} - удалить элемент из коллекции по его id
                save - сохранить коллекцию в файл
                execute_script {file_name} - исполнить скрипт из указанного скрипта
                update_id {id} - обновить значение элемента коллекции, id которого равен заданному
                insert_id index {element} - добавить новый элемент в заданную позицию
                average_oscars_count - вывести среднее значение поля oscarsCount для всех элементов коллекции
                max_mpaarating - вывести объект из коллекции, значение поля mpaaRating которого является максимальным
                count_mpaarating {mpaaRating} - вывести количество элементов, значение поля mpaaRating которых меньше заданного
                exit - выход из программы""";
    }

    public void ClearCommand() {
        movies.clear();
    }
    public void AddCommand(Movie movie) {
        int id = movies.isEmpty() ? 1 : movies.get(movies.size() - 1).getId() + 1;
        movie.setId(id);
        movies.add(movie);
    }
     public String UpdateMovieById(int id, Movie updatedMovie) {
        for (int i = 0; i < movies.size(); i++) {
            if (movies.get(i).getId() == id) {
                Movie movieToUpdate = movies.get(i);

                movieToUpdate.setName(updatedMovie.getName());
                movieToUpdate.setCoordinates(updatedMovie.getCoordinates());
                movieToUpdate.setOscarsCount(updatedMovie.getOscarsCount());
                movieToUpdate.setTotalBoxOffice(updatedMovie.getTotalBoxOffice());
                movieToUpdate.setGenre(updatedMovie.getGenre());
                movieToUpdate.setMpaaRating(updatedMovie.getMpaaRating());
                movieToUpdate.setScreenwriter(updatedMovie.getScreenwriter());


                return "Фильм с id  " + id + " успешно обновлен";
            }
        }
        return "Фильм с id " + id + " не найден";
    }



    public String ShowCommand() {


        if (movies.isEmpty()) {
            return "Коллекция фильмов пуста.";
        } else {
            return movies.stream()
                    .filter(Objects::nonNull) // исключаю  null-элементы
                    .map(Object::toString) // преобразую каждый объект movie в его строку
                    .collect(Collectors.joining("\n")); // объединяю строки в одну
        }
    }

    public String removeFirstElement() {
        if (!movies.isEmpty()) {
            Optional<Movie> firstMovie = movies.stream().findFirst();
            firstMovie.ifPresent(movie -> movies.remove(movie));
            return firstMovie.map(movie -> "Удаленный фильм: " + movie.toString())
                    .orElse("Не удалось удалить фильм.");
        } else {
            return "Список фильмов пуст. Фильм не был удален.";
        }
    }


    public String RemoveLastElement() {
        if (!movies.isEmpty()) {
            Movie lastMovie = movies.get(movies.size() - 1);
            movies = (ArrayList<Movie>) movies.stream()
                    .filter(movie -> !movie.equals(lastMovie))
                    .collect(Collectors.toList());
            return "Удаленный фильм: " + lastMovie.toString();
        } else {
            return "Список фильмов пуст. Фильм не был удален.";
        }
    }

    public void removeMovieById(int movieId) {
            movies.removeIf(movie -> movie.getId() == movieId);
        }



    public String getAverageOscarsCount() {
            if (!movies.isEmpty()) {
                OptionalDouble average = movies.stream().mapToDouble(Movie::getOscarsCount).average();
                if (average.isPresent()) {
                    double averageValue = average.getAsDouble();
                    return "average_oscars_count равен " + averageValue;
                } else {
                    return "Список фильмов пуст. Невозможно найти среднее значение поля average_count.";
                }
            } else {
                return "Список фильмов пуст. Невозможно найти среднее значение поля average_count.";
            }
        }

    public String mp() {
        if (!movies.isEmpty()) {
            Optional<Movie> movieWithMaxRating = movies.stream()
                    .filter(movie -> movie.getMpaaRating() != null)
                    .max(Comparator.comparing(movie -> movie.getMpaaRating().getValue()));
            return movieWithMaxRating.map(movie -> "Фильм с максимальным рейтингом MPAA: " + movie)
                    .orElse("В коллекции нет фильмов с заполненным рейтингом MPAA");
        } else {
            return "Список фильмов пуст. Невозможно найти Фильм с максимальным рейтингом MPAA";
        }
    }

    public String count_mp(int number) {
        int mpaaRating = number;
        if (mpaaRating != 6 && mpaaRating != 12 && mpaaRating != 18) {
            return "Выберите возрастное ограничение из предложенного 6, 12 или 18";
        }

        long count = movies.stream()
                .filter(movie -> movie.getMpaaRating().getValue() < mpaaRating)
                .count();

        return "Количество фильмов с MPAA меньше " + mpaaRating + ": " + count;
    }


public String InsertIdCommand (int index, Movie newMovie) {
    newMovie.setId(index);
    movies.add(index - 1, newMovie);
    for (int i = index; i < movies.size(); i++) {
        Movie movie = movies.get(i);
        movie.setId(movie.getId() + 1);
    }

    return "Фильм успешно добавлен в коллекцию.";
}

    public String execute_script(String filename) {
    StringBuilder result = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String command;
            CommandManager commandManager = new CommandManager();
            commandManager.cmdAdd();

            while ((command = reader.readLine()) != null) {
                if (!command.isEmpty()) {
                    String[] words = command.split(" ");
                    String commandName = words[0];

                    if (commandManager.isCommandExists(commandName)) {
                        if (commandName.equals("add")) {
                            readDataFromFile(filename);


                        } else if (commandName.equals("remove_by_id") || commandName.equals("insert_id") ||
                                commandName.equals("update_id") || commandName.equals("count_mpaarating")) {

                            if (commandName.equals("count_mpaarating")) {
                                String args = words[1];
                                Commands commandInstance = commandManager.getByName(commandName);
                                Response response = commandInstance.execute(new Request(commandName, args));
                                result.append(response.getResponse()).append("\n");

                            }
                            if (commandName.equals("remove_by_id")) {
                                int arg = Integer.parseInt(words[1]);
                                removeMovieById(arg);

                            }
                            if (commandName.equals("update_id")) {
                                int i = Integer.parseInt(words[1]);
                                updateFromFile(filename, i);
                            }
                            if (commandName.equals("insert_id")) {
                                int i = Integer.parseInt(words[1]);
                                insertFromFile(filename, i);

                                if (commandName.equals("exit")) {
                                    Commands commandInstance = commandManager.getByName(commandName);
                                    Response response = commandInstance.execute(new Request(commandName));
                                    result.append(response.getResponse()).append("\n");

                                }

                            } else {
                                result.append("Отсутствуют аргументы для команды: ").append(commandName).append("\n");
                            }

                        } else {
                            Commands commandInstance = commandManager.getByName(commandName);
                            Response response = commandInstance.execute(new Request(commandName));
                            result.append(response.getResponse()).append("\n");
                        }
                    } else {
                        result.append("Команда не найдена: ").append(commandName).append("\n");
                    }
                }
            }
        } catch (FileNotFoundException e ) {
            return "ERROR: Файл который вы ввели как аргумент не найден. Попробуйте еще раз";
        } catch (IOException e) {
            return "ERROR: Файл который вы ввели как аргумент не найден. Попробуйте еще раз";
        }

       return result.toString();
    }

    public String readDataFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            String nextLine;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("add")) {
                    nextLine = reader.readLine();
                    if (nextLine != null && !nextLine.isEmpty()) {
                        String[] fields = nextLine.split(",", -1);
                        int id = Integer.parseInt(fields[0]);
                        boolean movieExists = movies.stream().anyMatch(movie -> movie.getId() == id);
                        if (!movieExists) {
                            Movie movie = new Movie();
                            movie.setId(id);
                            movie.setName(fields[1]);
                            Coordinates coordinates = new Coordinates();
                            coordinates.setX(Integer.parseInt(fields[2]));
                            coordinates.setY(Integer.parseInt(fields[3]));
                            movie.setCoordinates(coordinates);
                            movie.setOscarsCount(Long.parseLong(fields[4]));
                            MpaaRating mpaaRating = MpaaRating.valueOf(fields[5]);
                            movie.setMpaaRating(mpaaRating);
                            MovieGenre genre = MovieGenre.valueOf(fields[6]);
                            movie.setGenre(genre);
                            LocalDate creationDate = LocalDate.parse(fields[7]);
                            movie.setCreationDate(creationDate);
                            movie.setTotalBoxOffice(Long.parseLong(fields[8]));
                            Person screenwriter = new Person();
                            screenwriter.setNm(fields[9]);
                            screenwriter.setWeight(Double.parseDouble(fields[10]));
                            Color eyeColor = Color.valueOf(fields[11]);
                            screenwriter.setEyeColor(eyeColor);
                            ColorHair hairColor = ColorHair.valueOf(fields[12]);
                            screenwriter.setHairColor(hairColor);
                            Location location = new Location();
                            location.setName(fields[13]);
                            location.setX(Integer.parseInt(fields[14]));
                            location.setY(Float.valueOf(Integer.parseInt(fields[15])));
                            screenwriter.setLocation(location);
                            movie.setScreenwriter(screenwriter);

                            movies.add(movie);
                        }
                    }
                }
            }
        } catch (IOException | NumberFormatException | DateTimeParseException e) {
            return "Error reading data from file: " + e.getMessage();
        }
        return "";
    }

    public String insertFromFile(String filename, int index) {
        Movie movie = new Movie();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            String nextLine;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("insert_id")) {
                    getMovieById(index);
                    nextLine = reader.readLine();
                    if (nextLine != null && !nextLine.isEmpty()) {
                        String[] fields = nextLine.split(",", -1);
                        movie.setName(fields[0]);
                        Coordinates coordinates = new Coordinates();
                        coordinates.setX(Integer.parseInt(fields[1]));
                        coordinates.setY(Integer.parseInt(fields[2]));
                        movie.setCoordinates(coordinates);
                        movie.setOscarsCount(Long.parseLong(fields[3]));
                        MpaaRating mpaaRating = MpaaRating.valueOf(fields[4]);
                        movie.setMpaaRating(mpaaRating);
                        MovieGenre genre = MovieGenre.valueOf(fields[5]);
                        movie.setGenre(genre);
                        LocalDate creationDate = LocalDate.parse(fields[6]);
                        movie.setCreationDate(creationDate);
                        movie.setTotalBoxOffice(Long.parseLong(fields[7]));
                        Person screenwriter = new Person();
                        screenwriter.setNm(fields[8]);
                        screenwriter.setWeight(Double.parseDouble(fields[9]));
                        Color eyeColor = Color.valueOf(fields[10]);
                        screenwriter.setEyeColor(eyeColor);
                        ColorHair hairColor = ColorHair.valueOf(fields[11]);
                        screenwriter.setHairColor(hairColor);
                        Location location = new Location();
                        location.setName(fields[12]);
                        location.setX(Integer.parseInt(fields[13]));
                        location.setY((float) Integer.parseInt(fields[14]));
                        screenwriter.setLocation(location);
                        movie.setScreenwriter(screenwriter);

                        movie.setId(index);
                        movies.add(index - 1, movie);
                        for (int i = index; i < movies.size(); i++) {
                            Movie m = movies.get(i);
                            m.setId(m.getId() + 1);
                        }

                        return "Фильм успешно добавлен в коллекцию.";
                    }
                }
            }
            return "";
        } catch (IOException e) {
            return "Ошибка в чтение файла: " + e.getMessage();
        }
    }

    public String updateFromFile(String filename, int id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            String nextLine;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("update_id")) {
                    nextLine = reader.readLine();
                    if (nextLine != null && !nextLine.isEmpty()) {
                        String[] fields = nextLine.split(",", -1);
                        Movie updatedMovie = getMovieById(id);
                        updatedMovie.setId(id);
                        updatedMovie.setName(fields[0]);
                        Coordinates coordinates = new Coordinates();
                        coordinates.setX(Integer.parseInt(fields[1]));
                        coordinates.setY(Integer.parseInt(fields[2]));
                        updatedMovie.setCoordinates(coordinates);
                        updatedMovie.setOscarsCount(Long.parseLong(fields[3]));
                        MpaaRating mpaaRating = MpaaRating.valueOf(fields[4]);
                        updatedMovie.setMpaaRating(mpaaRating);
                        MovieGenre genre = MovieGenre.valueOf(fields[5]);
                        updatedMovie.setGenre(genre);
                        LocalDate creationDate = LocalDate.parse(fields[6]);
                        updatedMovie.setCreationDate(creationDate);
                        updatedMovie.setTotalBoxOffice(Long.parseLong(fields[7]));
                        Person screenwriter = new Person();
                        screenwriter.setNm(fields[8]);
                        screenwriter.setWeight(Double.parseDouble(fields[9]));
                        Color eyeColor = Color.valueOf(fields[10]);
                        screenwriter.setEyeColor(eyeColor);
                        ColorHair hairColor = ColorHair.valueOf(fields[11]);
                        screenwriter.setHairColor(hairColor);
                        Location location = new Location();
                        location.setName(fields[12]);
                        location.setX(Integer.parseInt(fields[13]));
                        location.setY((float) Integer.parseInt(fields[14]));
                        screenwriter.setLocation(location);
                        updatedMovie.setScreenwriter(screenwriter);

                        updateMovieById(id,updatedMovie);
                    }
                }
            }
        } catch (IOException | NumberFormatException | DateTimeParseException e) {
            return "Ошибка в чтение файла: " + e.getMessage();
        }
        return "";
    }


    public Movie getMovieById(int id) {
        for (Movie movie : movies) {
            if (movie.getId() == id) {
                return movie;
            }
        }
        return null;
    }
    public String updateMovieById(int id, Movie movie) {
        for (int i = 0; i < movies.size(); i++) {
            if (movies.get(i).getId() == id) {
                movies.set(i, movie);
                return "Фильм с id  " + id + " успешно загружен";
            }
        }
        return "Фильм с id " + id + " не найден";
    }

}




