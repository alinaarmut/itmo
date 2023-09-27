package itmo.ser.commands.managers;

import itmo.common.description.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import itmo.ser.commands.dateBase.DataBaseHandler;

public class CollectionManager {
    public static ArrayList<Movie> movies = new ArrayList<>();
    private static final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private static LocalDateTime creationDate;

    public CollectionManager() {
        loadCollectionFromBd();
    }

    public static void loadCollectionFromBd() {
        try {
            creationDate = LocalDateTime.now();
            movies.addAll(DataBaseHandler.loadCollection());
        } catch (NullPointerException e) {
            System.out.println("Коллекция пустая");
        }
    }

    public String InfoCommand() {
        long nonNullCount = movies.stream()
                .filter(Objects::nonNull)
                .count();
        try {
            readWriteLock.readLock().lock();
            return ("-----------------------------------"
                    + "\nТип коллекции: " + movies.getClass().getSimpleName()
                    + "\nКоличество элементов в коллекции: " + nonNullCount
                    + "\nДата инициализации: " + new Date()
                    + "\n-----------------------------------\n");
        } finally {
            readWriteLock.readLock().unlock();
        }

    }

    public boolean checkExist(Integer Id) {
        try {
            for (Movie movie : CollectionManager.getMovies()) {
                if (Objects.equals(movie.getId(), Id))
                    return true;
            }
            return false;
        } catch (NullPointerException e) {
            throw new EmptyStackException();
        }
    }

    public static boolean checkUsersId(int id, String username) {
        String el = null;
        for (Movie movie : movies) {
            if (Objects.equals(movie.getId(), id)) {
                el = movie.getOwner();
                break;
            }
        }
        return username.equals(el);
    }

    public static ArrayList<Movie> getMovies() {
        return movies;
    }

    public static void ClearCommand() {
        try {
            readWriteLock.writeLock().lock();
            movies.clear();
        } finally {
            readWriteLock.writeLock().unlock();
        }


    }

    public void add(Movie movie) {
        try {
            readWriteLock.writeLock().lock();
            movies.add(movie);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public void removeById(Integer id) {
        try {
            readWriteLock.writeLock().lock();
            Iterator<Movie> i = movies.iterator();
            while (i.hasNext()) {
                Movie movie = i.next();
                if (Objects.equals(movie.getId(), id)) {
                    i.remove();
                    break;
                }
            }
        } finally {
            readWriteLock.writeLock().unlock();
        }

    }

    public static String ShowCommand() {
        try {
            readWriteLock.readLock().lock();
            StringBuilder information = new StringBuilder();
            for (Movie movie : movies) {
                information.append(movie.toString()).append("\n");
            }
            return information.toString();
        } finally {
            readWriteLock.readLock().unlock();
        }

    }


    public void update(Movie mv, Integer id) {
        try {
            readWriteLock.writeLock().lock();
            for (Movie movie : movies) {
                if (Objects.equals(movie.getId(), id)) {
                    movie.setName(mv.getName());
                    movie.setCoordinates(mv.getCoordinates());
                    movie.setOscarsCount(mv.getOscarsCount());
                    movie.setTotalBoxOffice(mv.getTotalBoxOffice());
                    movie.setGenre(mv.getGenre());
                    movie.setMpaaRating(mv.getMpaaRating());
                    movie.setScreenwriter(mv.getScreenwriter());
                }
            }

        } finally {
            readWriteLock.writeLock().unlock();
        }

    }

    public String getAverageOscarsCount() {
        try {
            readWriteLock.readLock().lock();
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
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public String mp() {
        try {
            readWriteLock.readLock().lock();
            if (!movies.isEmpty()) {
                Optional<Movie> movieWithMaxRating = movies.stream()
                        .filter(movie -> movie.getMpaaRating() != null)
                        .max(Comparator.comparing(movie -> movie.getMpaaRating().getValue()));
                return movieWithMaxRating.map(movie -> "Фильм с максимальным рейтингом MPAA: " + movie)
                        .orElse("В коллекции нет фильмов с заполненным рейтингом MPAA");
            } else {
                return "Список фильмов пуст. Невозможно найти Фильм с максимальным рейтингом MPAA";
            }
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public String count_mp(int number) {
        try {
            readWriteLock.readLock().lock();
            int mpaaRating = number;
            if (mpaaRating != 6 && mpaaRating != 12 && mpaaRating != 18) {
                return "Выберите возрастное ограничение из предложенного 6, 12 или 18";
            }

            long count = movies.stream()
                    .filter(movie -> movie.getMpaaRating().getValue() < mpaaRating)
                    .count();

            return "Количество фильмов с MPAA меньше " + mpaaRating + ": " + count;
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public Movie getLastMovie() {
        try {
            readWriteLock.readLock().lock();
            if (!movies.isEmpty()) {
                return movies.get(movies.size() - 1);
            } else {
                return null;
            }
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public void deleteLastMovie() {
        try {
            readWriteLock.readLock().lock();
            if (!movies.isEmpty()) {
                movies.remove(movies.size() - 1);
            }

        } finally {
            readWriteLock.writeLock().unlock();
        }

    }

    public Movie getFirstMovie() {
        try {
            readWriteLock.readLock().lock();
            if (!movies.isEmpty()) {
                return movies.get(0);
            }
            return null;

        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public void deleteFirstMovie() {
        try {
            readWriteLock.readLock().lock();

            if (!movies.isEmpty()) {
                movies.remove(0);

            }
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

}






