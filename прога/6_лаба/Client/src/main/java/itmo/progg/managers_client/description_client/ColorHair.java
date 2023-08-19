package itmo.progg.managers_client.description_client;

public class ColorHair extends Execution<itmo.common.description.ColorHair> {
    @Override
    public itmo.common.description.ColorHair build() {

        System.out.println("""
    Введите цвет волос из предложенных:
    BROWN,
    BLUE,
    WHITE""");
        String hairColorInput;
    itmo.common.description.ColorHair hairColor = null;
        do {
            hairColorInput = customScanner.nextLine().toUpperCase().trim();
            if (!hairColorInput.isEmpty()) {
                try {
                    hairColor = itmo.common.description.ColorHair.valueOf(hairColorInput);
                } catch (IllegalArgumentException e) {
                    System.out.println("Неправильный ввод константы. Выберите цвет волос из предложенного:");
                }
            }
        } while (hairColor == null);
        return hairColor;
    }
}
