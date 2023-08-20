package itmo.progg.managers_client.description_client;

import itmo.common.description.Coordinates;

import java.util.InputMismatchException;

public class CoordinatesClient extends Execution<Coordinates> {

    private Integer x; //Поле не может быть null
    private double y;


    // не уверена, что здесь нужны переменные
    @Override
    public Coordinates build() {
        return new Coordinates(askX(),
                askY());
    }

    private Integer askX() {
        int x;

        System.out.println("Введите координаты фильма:");

        System.out.println("Введите x:");
        while (true) {
            String input = customScanner.nextLine();
            if (!input.isEmpty()) {
                try {
                    x = Integer.parseInt(input);
                    break;
                } catch (InputMismatchException | NumberFormatException e) {
                    System.out.println("Неправильный ввод константы. Введите число:");
                }
            }
        }
        return x;
    }

    private double askY() {
        double y;
        System.out.println("Введите y:");
        while (true) {
            String input = customScanner.nextLine();
            if (!input.isEmpty()) {
                try {
                    y = Double.parseDouble(input);
                    break;
                } catch (InputMismatchException | NumberFormatException e) {
                    System.out.println("Неправильный ввод константы. Введите число:");
                }
            }
        }
        return y;
    }
}
