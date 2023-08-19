package itmo.progg.managers_client.description_client;

public class Color extends Execution<itmo.common.description.Color>{
    @Override
    public itmo.common.description.Color build() {
        System.out.println("""
    Введите цвет глаз из предложенного (если неизвестен, оставьте поле пустым):
    GREEN,
    BLACK,
    BLUE,
    YELLOW,
    WHITE""");
        String eyeColorInput;
        itmo.common.description.Color eyeColor = null;
        do {

            eyeColorInput = customScanner.nextLine().toUpperCase().trim();
            if (eyeColorInput.isEmpty()) {
                break;
            }
            try {
                eyeColor = itmo.common.description.Color.valueOf(eyeColorInput);
            } catch (IllegalArgumentException e) {
                System.out.println("Недопустимое значение цвета глаз. Выберите цвет глаз из предложенного:");
            }
        } while (eyeColor == null);
        return  eyeColor;
    }

}
