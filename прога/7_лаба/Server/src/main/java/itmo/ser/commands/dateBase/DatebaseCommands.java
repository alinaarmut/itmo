package itmo.ser.commands.dateBase;

public class DatebaseCommands {
    public static final String CREATE_ENUMS_TABLES = """
    CREATE TYPE COLOR AS ENUM (
        'GREEN',
        'BLACK',
        'BLUE',
        'YELLOW',
        'WHITE'
    );
    CREATE TYPE COLOR_HAIR AS ENUM (
        'BLUE',
        'WHITE',
        'BROWN'
    );
    CREATE TYPE GENRE AS ENUM (
        'COMEDY',
        'HORROR',
        'FANTASY'
    );
    CREATE TYPE MPAARATING AS ENUM (
        'G',
        'PC_13',
        'NC_17'
    );
""";

            public static final String CREATE_MAIN_TABLES = """
                CREATE TABLE IF NOT EXISTS Movie (
                    id SERIAL PRIMARY KEY,
                    movie_name TEXT NOT NULL ,
                    cord_x NUMERIC NOT NULL,
                    cord_y NUMERIC NOT NULL ,
                    creation_date TIMESTAMP NOT NULL ,
                    oscars_count BIGINT NOT NULL ,
                    totalBoxOffice BIGINT NOT NULL ,
                    genre GENRE,
                    mpaaRating MPAARATING,
                    person_name TEXT NOT NULL ,
                    person_weight INT NOT NULL ,
                    person_eye_color COLOR,
                    person_hair_color COLOR_HAIR,
                    person_location_x BIGINT NOT NULL,
                    person_location_y BIGINT NOT NULL,
                    person_location_name TEXT NOT NULL,
                    owner_username TEXT NOT NULL
                );
            
                """;
        public static final String CREATE_USERS_TABLE = """
            CREATE TABLE IF NOT EXISTS users (
                id SERIAL PRIMARY KEY,
                username TEXT NOT NULL UNIQUE,
                password TEXT NOT NULL,
                salt TEXT NOT NULL
            );""";
        public static final String DELETE_ALL_OBJECTS = "DELETE FROM Movie WHERE owner_username = ?;";
    public static final String DELETE_OBJECT_BY_ID = "DELETE FROM Movie WHERE owner_username = ? AND id = ?;";
    public static final String DELETE_OBJECT = "DELETE FROM Movie WHERE owner_username = ? AND id = ?";
    public static final String CHECK_USER = "SELECT * FROM users WHERE username = ?;";
    public static final String GET_OBJECTS = "SELECT * FROM Movie;";
    public static final String ADD_USER = "INSERT INTO users (username, password, salt) VALUES (?, ?, ?);";

    public static final String ADD_OBJECT = """
            INSERT INTO Movie(
            movie_name, cord_x, cord_y, creation_date, oscars_count,totalBoxOffice, genre,  mpaaRating,  person_name, person_weight,  
            person_eye_color,  person_hair_color,person_location_x, person_location_y,person_location_name, owner_username)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            RETURNING id;""";

    public static final String UPDATE_OBJECT = """
            UPDATE Movie SET (
            movie_name, cord_x, cord_y, creation_date, oscars_count,totalBoxOffice, genre,  mpaaRating,  person_name, person_weight,  
            person_eye_color,  person_hair_color,person_location_x, person_location_y,person_location_name)
            = (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) WHERE id = ? AND owner_username = ?;""";


}

