package itmo.progg.managers_client.description_client;

//import description.*;
import itmo.common.description.*;
import itmo.common.description.MovieGenre;
import itmo.common.description.MpaaRating;
import itmo.common.description.Person;
import itmo.progg.managers_client.CustomScanner;

import java.time.LocalDate;
import java.util.InputMismatchException;

public class MovieBuilder extends Execution<Movie> {

   // private Integer id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private long oscarsCount; //Значение поля должно быть больше 0
    private long totalBoxOffice; //Поле может быть null, Значение поля должно быть больше 0
    private MovieGenre genre; //Поле не может быть null
    private MpaaRating mpaaRating; //Поле не может быть null
    private Person screenwriter;
   private CustomScanner customScanner;

    public MovieBuilder( String name, Coordinates coordinates, LocalDate creationDate,
                  long oscarsCount, long totalBoxOffice, MovieGenre genre,
                  MpaaRating mpaaRating, Person screenwriter) {


        this.name = name;
        this.coordinates =  coordinates;
        this.creationDate = creationDate;
        this.oscarsCount = oscarsCount;
        this.totalBoxOffice = totalBoxOffice;
        this.genre = genre;
        this.mpaaRating = mpaaRating;
        this.screenwriter = screenwriter;
    }


    public MovieBuilder(CustomScanner customScanner ) {
        this.creationDate = java.time.LocalDate.now();
        this.customScanner =customScanner;

    }

    @Override
    public Movie build() {
      Movie movie = null;
        movie = new Movie(0,
                askName(), askCoordinates(), creationDate, askOscarsCount(), askTotalBoxOffice(), askGenre(),
                askMpaaRating(),
                askSscreenwriter());

        return movie;
    }
    private String askName(){
        String name;
        while (true){
            System.out.println("Введите имя");
            name = customScanner.nextLine().trim();
            if (name.isEmpty()){
                System.out.println("Имя не может быть пустым");
            }
            else{
                return name;
            }
        }
    }
    private Coordinates askCoordinates(){
        return new CoordinatesClient().build();
    }
    private MovieGenre askGenre(){
        return new itmo.progg.managers_client.description_client.MovieGenre().build();
    }
    private MpaaRating askMpaaRating(){
        return new itmo.progg.managers_client.description_client.MpaaRating().build();
    }
    private Person    askSscreenwriter() { return  new itmo.progg.managers_client.description_client.Person().build(); }
    private long askOscarsCount() {
        long oscarsCount;
        System.out.println("Введите количество оскаров:");
        do {
            String input = customScanner.nextLine();
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
        return oscarsCount;
    }
private long askTotalBoxOffice() {
        long totalBoxOffice;
    System.out.println("Введите общую кассу:");
    do {
        String input = customScanner.nextLine();
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
                customScanner.nextLine();
            }
        }
    } while (true);

    return totalBoxOffice;
}
}
