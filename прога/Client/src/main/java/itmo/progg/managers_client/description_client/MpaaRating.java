package itmo.progg.managers_client.description_client;

public class MpaaRating extends Execution<itmo.common.description.MpaaRating> {
    @Override
    public itmo.common.description.MpaaRating build() {
        System.out.println("""
                        Введите рейтинг MPAA:
                        G,
                        PG_13,
                        NC_17 """);
        String mpaaRatingInput;
        itmo.common.description.MpaaRating mpaaRating = null;
        do {
            mpaaRatingInput = customScanner.nextLine().toUpperCase().trim();
            try {
                mpaaRating = itmo.common.description.MpaaRating.valueOf(mpaaRatingInput);
            } catch (IllegalArgumentException e) {
                System.out.println("Недопустимое значение возрастного ограничения. Выберите жанр фильма из предложенного:");
            }
        } while (mpaaRating == null);
        return mpaaRating;
    }
}
