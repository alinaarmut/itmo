package itmo.progg.managers_client.description_client;

import java.util.InputMismatchException;

public class Location extends Execution<itmo.common.description.Location> {
    private  double x;
    private   Float y; //Поле не может быть null
    private String city; //Поле не может быть null

    @Override
    public itmo.common.description.Location build() {
        return new itmo.common.description.Location(askX(),askY(),askName());
    }
    private double askX() {
        double x;
        System.out.println("Введите местоположение:");

        System.out.println("Введите x:");
        while (true) {
            String input = customScanner.nextLine();
            if (!input.isEmpty()) {
                try {
                    x = Double.parseDouble(input);
                    break;
                } catch (InputMismatchException | NumberFormatException e) {
                    System.out.println("Неправильный ввод константы. Введите число:");
                }
            }
        }
        return x;
    }
    private Float askY() {
float y;
        System.out.println("Введите y:");
        while (true) {
            String input = customScanner.nextLine();
            if (!input.isEmpty()) {
                try {
                    y = Float.parseFloat(input);
                    break;
                } catch (InputMismatchException | NumberFormatException e) {
                    System.out.println("Неправильный ввод константы. Введите число:");
                }
            }
        }
        return y;
    }
    private String askName() {
        System.out.println("Введите название города:");
        String city;
        do {
            city =customScanner.nextLine().trim();
        } while (city == null || city.isEmpty());

        return city;
    }
}
