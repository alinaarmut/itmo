package itmo.progg.managers_client.description_client;

public class MovieGenre extends Execution<itmo.common.description.MovieGenre> {
    @Override
    public itmo.common.description.MovieGenre build() {
        System.out.println("""
                       Введите жанр фильма:
                       COMEDY,
                       HORROR,
                       FANTASY""");
        String genreInput;
        itmo.common.description.MovieGenre genre = null;
        do {
            genreInput = customScanner.nextLine().toUpperCase().trim();
            try {
                genre = itmo.common.description.MovieGenre.valueOf(genreInput);
            } catch (IllegalArgumentException e) {
                System.out.println("Недопустимое значение жанра. Выберите жанр фильма из предложенного:");
            }
        } while (genre == null);
        return genre;
    }
}
