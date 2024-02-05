package itmo.prog.description;
/**
 * Класс Movie
 */

import java.time.LocalDate;

public class Movie implements Comparable<Movie>{

    private Integer id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
     private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
     private java.time.LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
     private long oscarsCount; //Значение поля должно быть больше 0
     private long totalBoxOffice; //Поле может быть null, Значение поля должно быть больше 0
     private MovieGenre genre; //Поле не может быть null
     private MpaaRating mpaaRating; //Поле не может быть null
     private Person screenwriter;

    public Movie(int id, String name, Coordinates coordinates, LocalDate creationDate,
                 long oscarsCount, Long totalBoxOffice, MovieGenre genre,
                 MpaaRating mpaaRating, Person screenwriter) {

        this.id = id;
        this.name = name;
        this.coordinates =  coordinates;
        this.creationDate = LocalDate.from(creationDate);
        this.oscarsCount = oscarsCount;
        this.totalBoxOffice = totalBoxOffice;
        this.genre = genre;
        this.mpaaRating = mpaaRating;
        this.screenwriter = screenwriter;
    }

    public Movie() {

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

    @Override
    public int compareTo(Movie anotherMovie) {
        if (this.id == anotherMovie.id) {
            return 0;
        } else if (this.id < anotherMovie.id) {
            return -1;
        } else {
            return 1;
        }

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

    public LocalDate getCreationDate() {
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

    public void setCreationDate(LocalDate creationDate) {
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
                '}';
    }
}
