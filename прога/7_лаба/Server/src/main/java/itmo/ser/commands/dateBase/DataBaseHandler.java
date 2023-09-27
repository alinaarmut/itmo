package itmo.ser.commands.dateBase;
import itmo.common.description.*;
import itmo.common.description.managers.User;
import itmo.ser.commands.managers.CollectionManager;
import org.postgresql.util.PSQLException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Logger;

import static itmo.ser.commands.dateBase.DatebaseCommands.*;


public class DataBaseHandler {
    private static String URL;
    private static String username;
    private static String password;
    private static Connection connection;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrs" +
            "tuvwxyz0123456789<>?:@{!$%^&*()_+£$";
    private static final String PEPPER = "[g$J*(l;";
    private static Logger logger = Logger.getLogger(DataBaseHandler.class.getName());


    public DataBaseHandler() {
        connect();
        createUsers();
        createEnums();
        createMovie();
        loadCollection();
    }

    public void connect() {
        Properties info = new Properties();
        try (InputStream in = Files.newInputStream(Paths.get("db.properties"))) {
            info.load(in);
            URL = info.getProperty("url");
            username = info.getProperty("username");
            password = info.getProperty("password");
            connection = DriverManager.getConnection(URL, username, password);
            System.out.println("Успешно подключен к базе данных");
        } catch (SQLException | IllegalStateException | IOException e) {
            System.out.println(e.getMessage());
            System.out.println("Невозможно подключиться к базе данных");
            System.exit(1);
        }
    }

    public boolean registerUser(User user) throws SQLException, UnsupportedEncodingException, NoSuchAlgorithmException {
        if (userExists(user.getUsername())) return false;
        String userSalt = generateRandomString();
        PreparedStatement regStatement = connection.prepareStatement(DatebaseCommands.ADD_USER);
        regStatement.setString(1, user.getUsername());
        regStatement.setString(2, getSHA224Hash(user.getPassword()));
        regStatement.setString(3, userSalt);
        regStatement.executeUpdate();
        regStatement.close();
        System.out.println("добавлен новый пользователь username: " + user.getUsername());
        return true;
    }

    private boolean userExists(String username) throws SQLException {
        PreparedStatement checkStatement = connection.prepareStatement(DatebaseCommands.CHECK_USER);
        checkStatement.setString(1, username);
        ResultSet resultSet = checkStatement.executeQuery();
        return resultSet.next();
    }

    public boolean loginUser(User inputUser) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        try {
            String username1 = inputUser.getUsername();
            PreparedStatement getUser = connection.prepareStatement(DatebaseCommands.CHECK_USER);
            getUser.setString(1, username1);
            ResultSet resultSet = getUser.executeQuery();
            if (resultSet.next()) {
                String inputUserPassword = resultSet.getString("password");
                String inputUserSalt = resultSet.getString("salt");
                String checkPassword = getSHA224Hash(inputUser.getPassword());
                return checkPassword.equals(inputUserPassword);
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Неверная команда sql");
            e.printStackTrace();
            return false;
        }
    }

    public static boolean clear(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement(DatebaseCommands.DELETE_ALL_OBJECTS);
            statement.setString(1, user.getUsername());
            int stat = statement.executeUpdate();
            System.out.println("коллекция юзера " + user.getUsername() + " очищена");
            return stat != 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static boolean deleteObjectById(User user, int id) {
        try {
            PreparedStatement statement = connection.prepareStatement(DELETE_OBJECT);
            statement.setString(1, user.getUsername());
            statement.setInt(2, id);
            int stat = statement.executeUpdate();
            System.out.println("последний элемент коллекция юзера " + user.getUsername() + " удален");
            return stat > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static void createUsers() {
        try {
            connection.prepareStatement(DatebaseCommands.CREATE_USERS_TABLE).execute();
            System.out.println("Таблица users создана");
        } catch (SQLException e) {
            System.out.println("Какая-та ошибка в SQL запросе таблицы users");
        }

    }

    public static String getSHA224Hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-224");
            byte[] inputBytes = input.getBytes();
            md.update(inputBytes);
            byte[] hashBytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String generateRandomString() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(6);
        for (int i = 0; i < 6; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }

    public static void createMovie() {
        try {
            connection.prepareStatement(DatebaseCommands.CREATE_MAIN_TABLES).execute();
            System.out.println("Таблица movie создана");
        } catch (PSQLException e) {
            System.out.println("Таблицы уже существуют");
        } catch (SQLException e) {
            System.out.println("Какая-та ошибка в SQL запросе таблицы movie");
            e.printStackTrace();
        }
    }

    public static ArrayList<Movie> loadCollection() {
        try {
            PreparedStatement statement = connection.prepareStatement(GET_OBJECTS);
            ResultSet resultSet = statement.executeQuery();//exception
            ArrayList<Movie> movies = new ArrayList<>();
            while (resultSet.next()) {
                Coordinates coordinates = new Coordinates(
                        resultSet.getInt("cord_x"),
                        resultSet.getDouble("cord_y"));
                MovieGenre movieGenre = null;
                String typeGenre = resultSet.getString("genre");
                if (typeGenre != null) {
                    movieGenre = MovieGenre.valueOf(typeGenre);
                }
                MpaaRating mpaaRating = null;
                String typeMpaarating = resultSet.getString("mpaaRating");
                if (typeMpaarating != null) {
                    mpaaRating = MpaaRating.valueOf(typeMpaarating);
                }
                Color color = null;
                String typeColor = resultSet.getString("person_eye_color");
                if (typeColor != null) {
                    color = Color.valueOf(typeColor);
                }
                ColorHair colorHair = null;
                String typecolorHair = resultSet.getString("person_hair_color");
                if (typecolorHair != null) {
                    colorHair = ColorHair.valueOf(typecolorHair);
                }
                Location location = new Location(
                        resultSet.getDouble("person_location_x"),
                        resultSet.getFloat("person_location_y"),
                        resultSet.getString("person_location_name"));

                Person person = new Person(
                        resultSet.getString("person_name"),
                        resultSet.getDouble("person_weight"),
                        color,
                        colorHair,
                        location);

                Movie movie = new Movie(
                        resultSet.getInt("id"),
                        resultSet.getString("movie_name"),
                        coordinates,
                        (resultSet.getTimestamp("creation_date")).toLocalDateTime(),
                        resultSet.getLong("oscars_count"),
                        resultSet.getLong("totalBoxOffice"),
                        movieGenre,
                        mpaaRating,
                        person,
                        resultSet.getString("owner_username"));
                movies.add(movie);
            }
            System.out.println("Коллекция юзера загружена. Количество элементов - " + movies.size());
            return movies;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
            //System.out.println("SQL ошибка");

        }
    }


    public static void createEnums() {
        try {
            connection.prepareStatement(DatebaseCommands.CREATE_ENUMS_TABLES).execute();
            System.out.println("Enums созданы");
        } catch (SQLException e) {
            System.out.println("Enums уже созданы");
        }
    }

    public static boolean removeById(Integer id, User user) {
        try {
            PreparedStatement statement = connection.prepareStatement(DatebaseCommands.DELETE_OBJECT_BY_ID);
            statement.setString(1, user.getUsername());
            statement.setInt(2, id);
            int stat = statement.executeUpdate();
            //logger.info("объект удален из коллекцию");
            return stat != 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("объект не удален из коллекцию");
            return false;
        }
    }

    public static Integer addObject(Movie movie, User user) {
        try {
            PreparedStatement statement = connection.prepareStatement(DatebaseCommands.ADD_OBJECT);
            statement.setString(1, movie.getName());
            statement.setInt(2, movie.getCoordinates().getX());
            statement.setDouble(3, movie.getCoordinates().getY());
            statement.setTimestamp(4, Timestamp.valueOf(Timestamp.valueOf(movie.getCreationDate()).toLocalDateTime()));
            statement.setDouble(5, movie.getOscarsCount());
            statement.setLong(6, movie.getTotalBoxOffice());
            statement.setObject(7, movie.getGenre(), Types.OTHER);
            statement.setObject(8, movie.getMpaaRating(), Types.OTHER);
            statement.setString(9, movie.getScreenwriter().getNm());
            statement.setDouble(10, movie.getScreenwriter().getWeight());
            statement.setObject(11, movie.getScreenwriter().getEyeColor(), Types.OTHER);
            statement.setObject(12, movie.getScreenwriter().getHairColor(), Types.OTHER);
            statement.setDouble(13, movie.getScreenwriter().getLocation().getX());
            statement.setFloat(14, movie.getScreenwriter().getLocation().getY());
            statement.setString(15, movie.getScreenwriter().getLocation().getName());
            statement.setString(16, user.getUsername());
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                System.out.println("Объект не добавлен в коллекцию");
                return -1;
            }
            System.out.println("Объект добавлен в коллекцию");
            return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }

    }
    public static boolean update(Integer id, Movie movie, User user){
        try {
            LocalDateTime creationTime = LocalDateTime.now();
            PreparedStatement statement = connection.prepareStatement(DatebaseCommands.UPDATE_OBJECT);
            statement.setString(1, movie.getName());
            statement.setInt(2, movie.getCoordinates().getX());
            statement.setDouble(3, movie.getCoordinates().getY());
            statement.setTimestamp(4, Timestamp.valueOf(creationTime));
            statement.setDouble(5, movie.getOscarsCount());
            statement.setLong(6, movie.getTotalBoxOffice());
            statement.setObject(7, movie.getGenre(), Types.OTHER);
            statement.setObject(8, movie.getMpaaRating(), Types.OTHER);
            statement.setString(9, movie.getScreenwriter().getNm());
            statement.setDouble(10, movie.getScreenwriter().getWeight());
            statement.setObject(11, movie.getScreenwriter().getEyeColor(), Types.OTHER);
            statement.setObject(12, movie.getScreenwriter().getHairColor(), Types.OTHER);
            statement.setDouble(13, movie.getScreenwriter().getLocation().getX());
            statement.setFloat(14, movie.getScreenwriter().getLocation().getY());
            statement.setString(15, movie.getScreenwriter().getLocation().getName());
            statement.setInt(16, id);
            statement.setString(17, user.getUsername());
            int res = statement.executeUpdate();
            return res != 0;
        } catch (SQLException e) {
            logger.severe("Обновление объекта не произошло");
            e.printStackTrace();
            return false;
        }
    }





}
