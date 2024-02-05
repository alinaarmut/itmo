package itmo.prog.managers;
import itmo.prog.description.*;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
/**
 * Класс CollectionManager в котором содержится реализация всех команд
 */
public class CollectionManager {
    public static ArrayList<Movie> movies;
    private final Converter converter;

    public CollectionManager() {
        this.converter = new Converter();
    }
    public void ClearCommand() {
        movies.clear();
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

    public String InfoCommand() {
        return "Тип коллекции: " + movies.getClass().getSimpleName() + "\n" +
                "Количество элементов: " + movies.size() + "\n" +
                "Дата инициализации: " + new Date();
    }

    public String RemoveFirstElement() {
        if (!movies.isEmpty()) {
            Movie rm = movies.remove(0);
            return "Removed movie: " + rm.toString();
        } else {
            return "Список фильмов пуст. Фильм не был удален.";
        }
    }

    public String RemoveLastElement() {
        if (!movies.isEmpty()) {
            Movie removedMovie = movies.remove(movies.size() - 1);
            return "Removed movie: " + removedMovie.toString();
        } else {
            return "Список фильмов пуст. Фильм не был удален.";
        }
    }

    public void removeMovieById(int movieId) {
        Iterator<Movie> iterator = movies.iterator();
        while (iterator.hasNext()) {
            Movie movie = iterator.next();
            if (movie.getId() == movieId) {
                iterator.remove();
                return;
            }
        }
    }

    public boolean containsMovieById(int movieId) {
        Iterator<Movie> iterator = movies.iterator();
        while (iterator.hasNext()) {
            Movie movie = iterator.next();
            if (movie.getId() == movieId) {
                return true;
            }
        }
        return false;
    }

    public String getAverageOscarsCount() {
        double sum = 0.0;
        int count = 0;
        for (Movie movie : movies) {
            sum += movie.getOscarsCount();
            count++;
        }
        return String.valueOf(sum / count);
    }

    public String ShowCommand() {
        StringBuilder result = new StringBuilder();
        if (movies.isEmpty()) {
            result.append("Коллекция фильмов пуста.");
        } else {
            for (Movie movie : movies) {
                result.append(movie.toString()).append("\n");
            }
        }
        return result.toString();
    }

    public void SaveCommand() {
        converter.writeCollection(movies);
    }

    public String ExitCommand() {
        System.out.println("Программа завершена(без сохранения в файл)");
        System.exit(0);

        return "";
    }

    public String mp() {
        Movie movieWithMaxRating = null;
        for (Movie movie : movies) {
            if (movieWithMaxRating == null || movie.getMpaaRating().compareTo(movieWithMaxRating.getMpaaRating()) > 0) {
                movieWithMaxRating = movie;
            }
        }
        return "Фильм с максимальным рейтингом MPAA: " + movieWithMaxRating;
    }


    public String count_mp(int number) {
        Movie movieWithMaxRating = null;
        int mpaaRating = number;
        int count = 0;
        if (mpaaRating != 6 && mpaaRating != 12 && mpaaRating != 18) {
            return "Выберите возрастное ограничение из предложенного 6,12, или 18";
        }

        for (Movie movie : movies) {
            if (movie.getMpaaRating().getValue() < mpaaRating) {
                count++;
            }
            if (movieWithMaxRating == null || movie.getMpaaRating().compareTo(movieWithMaxRating.getMpaaRating()) > 0) {
                movieWithMaxRating = movie;
            }
        }

        return "Количество фильмов с MPAA меньше " + mpaaRating + ": " + count;

    }

    public String putMovie() {

        CustomScanner scanner = new CustomScanner(System.in);
        int x = 0;
        int y;
        long oscarsCount;
        long totalBoxOffice;
        double x1;
        float y1;
        String input;



        System.out.println("Введите название фильма:");
        String name;
        do {
            name = scanner.nextLine();
            if (!name.isEmpty()) {
                break;
            }
            System.out.println("Имя фильма не может быть пустым. Введите название фильма:");
        } while (true);



            System.out.println("Введите координаты фильма:");

            System.out.println("Введите x:");

            do {
                input = scanner.nextLine();
                if (!input.isEmpty()) {
                    try {
                        x = Integer.parseInt(input);
                        break;
                    } catch (InputMismatchException | NumberFormatException e ) {
                        System.out.println("Неправильный ввод константы. Введите число:");
                    }

                }

            } while (true);


            System.out.println("Введите y:");
            do {
                input = scanner.nextLine();
                if (!input.isEmpty()) {
                    try {
                        y = Integer.parseInt(input);
                        break;
                    } catch (InputMismatchException | NumberFormatException e) {
                        System.out.println("Неправильный ввод константы. Введите число:");
                    }
                }
            } while (true);

            Coordinates coordinates = new Coordinates(x, y);
            LocalDate creationDate = LocalDate.now();

            System.out.println("Введите количество оскаров:");
            do {
                input = scanner.nextLine();
                if (!input.isEmpty()) {
                    try {
                        oscarsCount = Long.parseLong(input);
                        if (oscarsCount <= 0) {
                            System.out.println("Количество оскаров должно быть больше 0.");
                        } else {
                            break;
                        }
                    } catch (InputMismatchException | NumberFormatException e) {
                        System.out.println("Неправильный ввод константы. Введите число:");
                    }
                }
            } while (true);

            System.out.println("Введите общую кассу:");
            do {
                input = scanner.nextLine();
                if (!input.isEmpty()) {
                    try {
                        totalBoxOffice = Long.parseLong(input);
                        if (totalBoxOffice <= 0) {
                            System.out.println("Количество BoxOffice должно быть больше 0.");
                        } else {
                            break;
                        }
                    } catch (InputMismatchException | NumberFormatException e) {
                        System.out.println("Неправильный ввод константы. Введите число:");
                        scanner.nextLine();
                    }
                }
            } while (true);


        System.out.println("""
                       Введите жанр фильма:
                       COMEDY,
                       HORROR,
                       FANTASY""");
        String genreInput;
        MovieGenre genre = null;
        do {
            genreInput = scanner.nextLine().toUpperCase().trim();
            try {
                genre = MovieGenre.valueOf(genreInput);
            } catch (IllegalArgumentException e) {
                System.out.println("Недопустимое значение жанра. Выберите жанр фильма из предложенного:");
            }
        } while (genre == null);

        System.out.println("""
                        Введите рейтинг MPAA:
                        G,
                        PG_13,
                        NC_17 """);
        String mpaaRatingInput;
        MpaaRating mpaaRating = null;
        do {
            mpaaRatingInput = scanner.nextLine().toUpperCase().trim();
            try {
                mpaaRating = MpaaRating.valueOf(mpaaRatingInput);
            } catch (IllegalArgumentException e) {
                System.out.println("Недопустимое значение возрастного ограничения. Выберите жанр фильма из предложенного:");
            }
        } while (mpaaRating == null);

        System.out.println("Введите имя режиссера:");
        String nm;
        do {
            nm = scanner.nextLine().trim();
            if (!nm.isEmpty()) {
                break;
            }
        } while (true);


        double weight;
        System.out.println("Введите вес:");
        do {
            input = scanner.nextLine();
            if (!input.isEmpty()) {
                try {
                    weight = Double.parseDouble(input);
                    if (weight <= 0) {
                        System.out.println("Вес должен быть больше нуля. Введите корректное значение");
                    } else {
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Неправильный ввод константы. Введите число:");
                }
            }
        } while (true);


        System.out.println("""
    Введите цвет глаз из предложенного (если неизвестен, оставьте поле пустым):
    GREEN,
    BLACK,
    BLUE,
    YELLOW,
    WHITE""");
        String eyeColorInput;
        Color eyeColor = null;
        do {
            eyeColorInput = scanner.nextLine().toUpperCase().trim();
            if (eyeColorInput.isEmpty()) {
                break;
            }
            try {
                eyeColor = Color.valueOf(eyeColorInput);
            } catch (IllegalArgumentException e) {
                System.out.println("Недопустимое значение цвета глаз. Выберите цвет глаз из предложенного:");
            }
        } while (eyeColor == null);


        System.out.println("""
    Введите цвет волос из предложенных:
    BROWN,
    BLUE,
    WHITE""");
        String hairColorInput;
        ColorHair hairColor = null;
        do {
            hairColorInput = scanner.nextLine().toUpperCase().trim();
            if (!hairColorInput.isEmpty()) {
                try {
                    hairColor = ColorHair.valueOf(hairColorInput);
                } catch (IllegalArgumentException e) {
                    System.out.println("Неправильный ввод константы. Выберите цвет волос из предложенного:");
                }
            }
        } while (hairColor == null);




        System.out.println("Введите местоположение x:");

        do {
            input = scanner.nextLine();
            if (!input.isEmpty()) {
                try {
                    x1 = Float.parseFloat(input);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Неправильный ввод константы. Введите число:");
                }
            }
        } while (true);

            System.out.println("Введите местоположение y:");
        do {
            input = scanner.nextLine();
            if (!input.isEmpty()) {
                try {
                    y1 = Float.parseFloat(input);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Неправильный ввод константы. Введите число:");
                }
            }
        } while (true);




        System.out.println("Введите название города:");
        String city;
        do {
            city = scanner.nextLine().trim();
        } while (city == null || city.isEmpty());


            Location location = new Location(x1, y1, city);

            int newId = movies.stream()
                    .mapToInt(Movie::getId)
                    .max()
                    .orElse(0) + 1 ;
            Person screenwriter = new Person(nm, weight, eyeColor, hairColor, location);
            Movie movie = new Movie(newId, name, coordinates, creationDate, oscarsCount, totalBoxOffice, genre, mpaaRating, screenwriter);
            movies.add(movie);

        return "Фильм успешно добавлен: " + movie;
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

    public Movie getMovieById(int id) {
        for (Movie movie : movies) {
            if (movie.getId() == id) {
                return movie;
            }
        }
        return null;
    }

    public String updateMovieByyId(int id) {
        Movie movie = getMovieById(id);
        if (movie == null) {
            return "Фильм с id " + id + " не найден";
        }
        Scanner scanner = new Scanner(System.in);
        int x = 0;
        int y;
        long oscarsCount;
        long totalBoxOffice;
        double x1;
        float y1;
        String input;


        System.out.println("Введите название фильма:");
        String name;
        do {
            name = scanner.nextLine();
            if (!name.isEmpty()) {
                break;
            }
            System.out.println("Имя фильма не может быть пустым. Введите название фильма:");
        } while (true);



        System.out.println("Введите координаты фильма:");

        System.out.println("Введите x:");

        do {
            input = scanner.nextLine();
            if (!input.isEmpty()) {
                try {
                    x = Integer.parseInt(input);
                    break;
                } catch (InputMismatchException | NumberFormatException e ) {
                    System.out.println("Неправильный ввод константы. Введите число:");
                }

            }

        } while (true);


        System.out.println("Введите y:");
        do {
            input = scanner.nextLine();
            if (!input.isEmpty()) {
                try {
                    y = Integer.parseInt(input);
                    break;
                } catch (InputMismatchException | NumberFormatException e) {
                    System.out.println("Неправильный ввод константы. Введите число:");
                }
            }
        } while (true);

        Coordinates coordinates = new Coordinates(x, y);
        LocalDate creationDate = LocalDate.now();

        System.out.println("Введите количество оскаров:");
        do {
            input = scanner.nextLine();
            if (!input.isEmpty()) {
                try {
                    oscarsCount = Long.parseLong(input);
                    if (oscarsCount <= 0) {
                        System.out.println("Количество оскаров должно быть больше 0.");
                    } else {
                        break;
                    }
                } catch (InputMismatchException | NumberFormatException e) {
                    System.out.println("Неправильный ввод константы. Введите число:");
                }
            }
        } while (true);

        System.out.println("Введите общую кассу:");
        do {
            input = scanner.nextLine();
            if (!input.isEmpty()) {
                try {
                    totalBoxOffice = Long.parseLong(input);
                    if (totalBoxOffice <= 0) {
                        System.out.println("Количество BoxOffice должно быть больше 0.");
                    } else {
                        break;
                    }
                } catch (InputMismatchException | NumberFormatException e) {
                    System.out.println("Неправильный ввод константы. Введите число:");
                    scanner.nextLine();
                }
            }
        } while (true);


        System.out.println("""
                       Введите жанр фильма:
                       COMEDY,
                       HORROR,
                       FANTASY""");
        String genreInput;
        MovieGenre genre = null;
        do {
            genreInput = scanner.nextLine().toUpperCase().trim();
            try {
                genre = MovieGenre.valueOf(genreInput);
            } catch (IllegalArgumentException e) {
                System.out.println("Недопустимое значение жанра. Выберите жанр фильма из предложенного:");
            }
        } while (genre == null);

        System.out.println("""
                        Введите рейтинг MPAA:
                        G,
                        PG_13,
                        NC_17 """);
        String mpaaRatingInput;
        MpaaRating mpaaRating = null;
        do {
            mpaaRatingInput = scanner.nextLine().toUpperCase().trim();
            try {
                mpaaRating = MpaaRating.valueOf(mpaaRatingInput);
            } catch (IllegalArgumentException e) {
                System.out.println("Недопустимое значение возрастного ограничения. Выберите жанр фильма из предложенного:");
            }
        } while (mpaaRating == null);

        System.out.println("Введите имя режиссера:");
        String nm;
        do {
            nm = scanner.nextLine().trim();
            if (!nm.isEmpty()) {
                break;
            }
        } while (true);


        double weight;
        System.out.println("Введите вес:");
        do {
            input = scanner.nextLine();
            if (!input.isEmpty()) {
                try {
                    weight = Double.parseDouble(input);
                    if (weight <= 0) {
                        System.out.println("Вес должен быть больше нуля. Введите корректное значение");
                    } else {
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Неправильный ввод константы. Введите число:");
                }
            }
        } while (true);


        System.out.println("""
    Введите цвет глаз из предложенного (если неизвестен, оставьте поле пустым):
    GREEN,
    BLACK,
    BLUE,
    YELLOW,
    WHITE""");
        String eyeColorInput;
        Color eyeColor = null;
        do {
            eyeColorInput = scanner.nextLine().toUpperCase().trim();
            if (eyeColorInput.isEmpty()) {
                break;
            }
            try {
                eyeColor = Color.valueOf(eyeColorInput);
            } catch (IllegalArgumentException e) {
                System.out.println("Недопустимое значение цвета глаз. Выберите цвет глаз из предложенного:");
            }
        } while (eyeColor == null);


        System.out.println("""
    Введите цвет волос из предложенных:
    BROWN,
    BLUE,
    WHITE""");
        String hairColorInput;
        ColorHair hairColor = null;
        do {
            hairColorInput = scanner.nextLine().toUpperCase().trim();
            if (!hairColorInput.isEmpty()) {
                try {
                    hairColor = ColorHair.valueOf(hairColorInput);
                } catch (IllegalArgumentException e) {
                    System.out.println("Неправильный ввод константы. Выберите цвет волос из предложенного:");
                }
            }
        } while (hairColor == null);




        System.out.println("Введите местоположение x:");

        do {
            input = scanner.nextLine();
            if (!input.isEmpty()) {
                try {
                    x1 = Float.parseFloat(input);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Неправильный ввод константы. Введите число:");
                }
            }
        } while (true);

        System.out.println("Введите местоположение y:");
        do {
            input = scanner.nextLine();
            if (!input.isEmpty()) {
                try {
                    y1 = Float.parseFloat(input);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Неправильный ввод константы. Введите число:");
                }
            }
        } while (true);




        System.out.println("Введите название города:");
        String city;
        do {
            city = scanner.nextLine().trim();
        } while (city == null || city.isEmpty());


        Location location = new Location(x1, y1, city);

        Person screenwriter = new Person(nm, weight, eyeColor, hairColor, location);
        Movie updatedMovie = new Movie(id, name, coordinates, creationDate, oscarsCount, totalBoxOffice, genre, mpaaRating, screenwriter);

        return updateMovieById(id, updatedMovie);
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
                        Movie movie = new Movie();
                        movie.setId(Integer.parseInt(fields[0]));
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
        } catch (IOException | NumberFormatException | DateTimeParseException e) {
            return "Error reading data from file: " + e.getMessage();
        }
        return "";
    }
    public String execute_script(String filename) {

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String command;

            while ((command = reader.readLine()) != null) {
                if (!command.isEmpty() && !command.equals("exit")) {
                    String[] words = command.split(" ");
                    String commandName = words[0];
                    if (words.length > 1 && (commandName.equals("remove_by_id") ||
                            commandName.equals("insert_id") || commandName.equals("update_id") || commandName.equals("count_mpaarating"))) {
                        int i = Integer.parseInt(words[1]);
                        switch (commandName) {
                            case "remove_by_id" -> removeMovieById(i);
                            case "insert_id" -> {
                                insertFromFile(filename, i);
                            }
                            case "update_id" -> {
                                updateFromFile(filename, i);
                            }
                            case "count_mpaarating" -> {
                                count_mp(i);
                                System.out.println(count_mp(i));
                            }
                        }
                    } else {
                        switch (commandName) {
                            case "help" -> {
                                HelpCommand();
                                System.out.println(HelpCommand());
                            }
                            case "info" -> {
                                InfoCommand();
                                System.out.println(InfoCommand());
                            }
                            case "show" -> {
                                ShowCommand();
                                System.out.println(ShowCommand());

                            }
                            case "clear" -> {
                                ClearCommand();
                                System.out.println("Коллекция очищена");

                            }
                            case "remove_last" -> RemoveLastElement();
                            case "remove_first" -> RemoveFirstElement();
                            case "average_oscars_count" -> {
                                getAverageOscarsCount();
                                System.out.println(getAverageOscarsCount());
                            }
                            case "save" -> SaveCommand();

                            case "add" -> {
                                readDataFromFile(filename);
                            }
                            case "exit" -> ExitCommand();

                            case "max_mpaarating" -> {
                                mp();
                                System.out.println(mp());
                            }
                            default -> System.out.println(" ");
                                    //-> System.out.println("Команда не найдена");
                        }
                    }
                }
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return "";
    }
    public String insert_id(int index) {
        Movie movie = getMovieById(index);
        if (movie == null) {
            return "Фильм с id " + index + " не найден";
        }
        Scanner scanner = new Scanner(System.in);
        int x = 0;
        int y;
        long oscarsCount;
        long totalBoxOffice;
        double x1;
        float y1;
        String input;




        System.out.println("Введите название фильма:");
        String name;
        do {
            name = scanner.nextLine();
            if (!name.isEmpty()) {
                break;
            }
            System.out.println("Имя фильма не может быть пустым. Введите название фильма:");
        } while (true);



        System.out.println("Введите координаты фильма:");

        System.out.println("Введите x:");

        do {
            input = scanner.nextLine();
            if (!input.isEmpty()) {
                try {
                    x = Integer.parseInt(input);
                    break;
                } catch (InputMismatchException | NumberFormatException e ) {
                    System.out.println("Неправильный ввод константы. Введите число:");
                }

            }

        } while (true);


        System.out.println("Введите y:");
        do {
            input = scanner.nextLine();
            if (!input.isEmpty()) {
                try {
                    y = Integer.parseInt(input);
                    break;
                } catch (InputMismatchException | NumberFormatException e) {
                    System.out.println("Неправильный ввод константы. Введите число:");
                }
            }
        } while (true);

        Coordinates coordinates = new Coordinates(x, y);
        LocalDate creationDate = LocalDate.now();

        System.out.println("Введите количество оскаров:");
        do {
            input = scanner.nextLine();
            if (!input.isEmpty()) {
                try {
                    oscarsCount = Long.parseLong(input);
                    if (oscarsCount <= 0) {
                        System.out.println("Количество оскаров должно быть больше 0.");
                    } else {
                        break;
                    }
                } catch (InputMismatchException | NumberFormatException e) {
                    System.out.println("Неправильный ввод константы. Введите число:");
                }
            }
        } while (true);

        System.out.println("Введите общую кассу:");
        do {
            input = scanner.nextLine();
            if (!input.isEmpty()) {
                try {
                    totalBoxOffice = Long.parseLong(input);
                    if (totalBoxOffice <= 0) {
                        System.out.println("Количество BoxOffice должно быть больше 0.");
                    } else {
                        break;
                    }
                } catch (InputMismatchException | NumberFormatException e) {
                    System.out.println("Неправильный ввод константы. Введите число:");
                    scanner.nextLine();
                }
            }
        } while (true);


        System.out.println("""
                       Введите жанр фильма:
                       COMEDY,
                       HORROR,
                       FANTASY""");
        String genreInput;
        MovieGenre genre = null;
        do {
            genreInput = scanner.nextLine().toUpperCase().trim();
            try {
                genre = MovieGenre.valueOf(genreInput);
            } catch (IllegalArgumentException e) {
                System.out.println("Недопустимое значение жанра. Выберите жанр фильма из предложенного:");
            }
        } while (genre == null);

        System.out.println("""
                        Введите рейтинг MPAA:
                        G,
                        PG_13,
                        NC_17 """);
        String mpaaRatingInput;
        MpaaRating mpaaRating = null;
        do {
            mpaaRatingInput = scanner.nextLine().toUpperCase().trim();
            try {
                mpaaRating = MpaaRating.valueOf(mpaaRatingInput);
            } catch (IllegalArgumentException e) {
                System.out.println("Недопустимое значение возрастного ограничения. Выберите жанр фильма из предложенного:");
            }
        } while (mpaaRating == null);

        System.out.println("Введите имя режиссера:");
        String nm;
        do {
            nm = scanner.nextLine().trim();
            if (!nm.isEmpty()) {
                break;
            }
        } while (true);


        double weight;
        System.out.println("Введите вес:");
        do {
            input = scanner.nextLine();
            if (!input.isEmpty()) {
                try {
                    weight = Double.parseDouble(input);
                    if (weight <= 0) {
                        System.out.println("Вес должен быть больше нуля. Введите корректное значение");
                    } else {
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Неправильный ввод константы. Введите число:");
                }
            }
        } while (true);


        System.out.println("""
    Введите цвет глаз из предложенного (если неизвестен, оставьте поле пустым):
    GREEN,
    BLACK,
    BLUE,
    YELLOW,
    WHITE""");
        String eyeColorInput;
        Color eyeColor = null;
        do {
            eyeColorInput = scanner.nextLine().toUpperCase().trim();
            if (eyeColorInput.isEmpty()) {
                break;
            }
            try {
                eyeColor = Color.valueOf(eyeColorInput);
            } catch (IllegalArgumentException e) {
                System.out.println("Недопустимое значение цвета глаз. Выберите цвет глаз из предложенного:");
            }
        } while (eyeColor == null);


        System.out.println("""
    Введите цвет волос из предложенных:
    BROWN,
    BLUE,
    WHITE""");
        String hairColorInput;
        ColorHair hairColor = null;
        do {
            hairColorInput = scanner.nextLine().toUpperCase().trim();
            if (!hairColorInput.isEmpty()) {
                try {
                    hairColor = ColorHair.valueOf(hairColorInput);
                } catch (IllegalArgumentException e) {
                    System.out.println("Неправильный ввод константы. Выберите цвет волос из предложенного:");
                }
            }
        } while (hairColor == null);




        System.out.println("Введите местоположение x:");

        do {
            input = scanner.nextLine();
            if (!input.isEmpty()) {
                try {
                    x1 = Float.parseFloat(input);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Неправильный ввод константы. Введите число:");
                }
            }
        } while (true);

        System.out.println("Введите местоположение y:");
        do {
            input = scanner.nextLine();
            if (!input.isEmpty()) {
                try {
                    y1 = Float.parseFloat(input);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Неправильный ввод константы. Введите число:");
                }
            }
        } while (true);




        System.out.println("Введите название города:");
        String city;
        do {
            city = scanner.nextLine().trim();
        } while (city == null || city.isEmpty());


        Location location = new Location(x1, y1, city);


        Person screenwriter = new Person(nm, weight, eyeColor, hairColor, location);
        Movie newMovie = new Movie(index, name, coordinates, creationDate, oscarsCount, totalBoxOffice, genre, mpaaRating, screenwriter);
        if (index < 0 || index > movies.size()) {
            return "Неправильный индекс. Фильм не был добавлен.";
        }
        newMovie.setId(index);
        movies.add(index - 1, newMovie);
        for (int i = index; i < movies.size(); i++) {
            movie = movies.get(i);
            movie.setId(movie.getId() + 1);
        }
        return "Фильм успешно добавлен в коллекцию.";
    }


    public String updateFromFile(String filename, int i) {
        Movie movie;
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            String nextLine;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("update_id")) {
                    nextLine = reader.readLine();
                    if (nextLine != null && !nextLine.isEmpty()) {
                        String[] fields = nextLine.split(",", -1);
                        movie = new Movie();
                        movie = getMovieById(i);
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
                        return updateMovieById(i, movie);
                    }
                }
            }
        } catch (IOException | NumberFormatException | DateTimeParseException e) {
            return "Ошибка в чтение файла: " + e.getMessage();
        }
        return  "";
    }

    public String insertFromFile(String filename, int index) {
        Movie movie = getMovieById(index);
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            String nextLine;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("insert_id")) {
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

                            index++;
                        }
                }
            }
            return "";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }






}















