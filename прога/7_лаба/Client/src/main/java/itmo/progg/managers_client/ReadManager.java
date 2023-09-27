package itmo.progg.managers_client;

public class ReadManager {

    public String readNameCanNotBeNull(String str) {
        CustomScanner customScanner = null;
        String name;
        while (true) {
            System.out.println(str);
            name = customScanner.nextLine().trim();
            if (name.equals("") || !name.matches("^[a-zA-Z-А-Яа-я]*$")) {
                System.out.println("Имя не может быть пустой строкой/иными знаками, кроме букв");
            } else {
                return name;
            }
        }
    }
}
