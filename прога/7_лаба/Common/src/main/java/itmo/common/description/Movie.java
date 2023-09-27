package itmo.common.description;

import java.io.Serializable;

import java.time.LocalDateTime;
import java.util.Objects;

public class Movie implements Serializable,Comparable<Movie> {

    private Integer id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private long oscarsCount; //Значение поля должно быть больше 0
    private long totalBoxOffice; //Поле может быть null, Значение поля должно быть больше 0
    private MovieGenre genre; //Поле не может быть null
    private MpaaRating mpaaRating; //Поле не может быть null
    private Person screenwriter;
    private String owner;


    public Movie( int id, String name, Coordinates coordinates, LocalDateTime creationDate,
                 long oscarsCount, long totalBoxOffice, MovieGenre genre,
                 MpaaRating mpaaRating, Person screenwriter) {


        this.name = name;
        this.coordinates =  coordinates;
        this.creationDate = LocalDateTime.now();
        this.oscarsCount = oscarsCount;
        this.totalBoxOffice = totalBoxOffice;
        this.genre = genre;
        this.mpaaRating = mpaaRating;
        this.screenwriter = screenwriter;
        this.id =id;

    }
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
    public Movie(String s, Coordinates coordinates,
                 LocalDateTime creationDate, long l,
                 long oscarsCount,
                 MovieGenre movieGenre, MpaaRating mpaaRating, Person person) {
    }

    public Movie() {

    }

    public Movie(int id, String name, Coordinates coordinates, LocalDateTime creationDate, long oscarsCount, long totalBoxOffice, MovieGenre genre, MpaaRating mpaaRating, Person screenwriter, String owner) {
        this.name = name;
        this.coordinates =  coordinates;
        this.creationDate = LocalDateTime.now();
        this.oscarsCount = oscarsCount;
        this.totalBoxOffice = totalBoxOffice;
        this.genre = genre;
        this.mpaaRating = mpaaRating;
        this.screenwriter = screenwriter;
        this.id =id;
        this.owner = owner;

    }


    @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Movie movie = (Movie) o;
            return Objects.equals(id, movie.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }




    public void setTotalBoxOffice(long totalBoxOffice) {
        this.totalBoxOffice = totalBoxOffice;
    }

    public void setOscarsCount(long oscarsCount) {
        this.oscarsCount = oscarsCount;
    }

    public void setScreenwriter(Person screenwriter) {
        this.screenwriter = screenwriter;
    }


    public int getId() {
        return id;
    }


    public Long getTotalBoxOffice() {
        return totalBoxOffice;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Person getScreenwriter() {
        return screenwriter;
    }

    public MovieGenre getGenre() {
        return genre;
    }

    public MpaaRating getMpaaRating() {
        return mpaaRating;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public long getOscarsCount() {
        return oscarsCount;
    }
    public void setName(String name) {
        this.name = name;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public void setCoordinates(Coordinates coordinates) {

        this.coordinates = coordinates;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setGenre(MovieGenre genre) {
        this.genre = genre;
    }

    public void setMpaaRating(MpaaRating mpaaRating) {

        this.mpaaRating = mpaaRating;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", oscarsCount=" + oscarsCount +
                ", totalBoxOffice=" + totalBoxOffice +
                ", genre=" + genre +
                ", mpaaRating=" + mpaaRating +
                ", screenwriter=" + screenwriter +
                ",owner=" + owner +
                '}';
    }
     @Override
        public int compareTo(Movie o) {
            return this.name.compareTo(o.name);
        }

}

