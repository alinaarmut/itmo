package itmo.progg.managers_client.description_client;

import itmo.common.description.Color;
import itmo.common.description.ColorHair;
import itmo.common.description.Location;

public class Person extends Execution<itmo.common.description.Person> {
    private  String nm; //Поле не может быть null, Строка не может быть пустой
    private  Double weight; //Поле может быть null, Значение поля должно быть больше 0
    private Color eyeColor; //Поле может быть null
    private ColorHair hairColor; //Поле не может быть null
    private Location location; //Поле не может быть null

    @Override
    public itmo.common.description.Person build() {
        return new itmo.common.description.Person(nm(),askWeight(),askEyeColor(),HairColor(),Location());
    }
    private String nm(){
        System.out.println("Введите имя режиссера:");
        String nm;
        do {
            nm = customScanner.nextLine().trim();
            if (!nm.isEmpty()) {
                break;
            }
        } while (true);
        return nm;
    }
    private Color askEyeColor(){
        return new itmo.progg.managers_client.description_client.Color().build();
    }
    private ColorHair HairColor(){
        return new itmo.progg.managers_client.description_client.ColorHair().build();
    }
    private Location Location(){
        return new itmo.progg.managers_client.description_client.Location().build();
    }
    private Double askWeight (){
        double weight;
        System.out.println("Введите вес:");
        do {
           String input = customScanner.nextLine();
            if (!input.isEmpty()) {
                try {
                    weight = Double.parseDouble(input);
                    if (weight <= 0) {
                        System.out.println("Вес должен быть больше нуля. Введите корректное значение");
                    } else {
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Неправильный ввод константы. Введите число:");
                }
            }
        } while (true);
        return weight;
    }
}
