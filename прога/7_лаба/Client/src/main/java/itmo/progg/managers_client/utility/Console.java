package itmo.progg.managers_client.utility;

import java.util.Scanner;

public class Console {
    private static boolean fileMode = false;

    public Console() {

    }

    public static boolean isFileMode() {
        return fileMode;
    }

    public static void setFileMode(boolean fileMode) {
        Console.fileMode = fileMode;
    }
    public Integer readInteger() {
        Scanner scanner = new Scanner(System.in);
        Integer number = Integer.valueOf(scanner.nextLine().trim());
        return number;
    }

    public String readLine() {
        Scanner scanner = new Scanner(System.in);
        String text = scanner.nextLine().trim();
        return text;
    }

    public void write(String text) {
        System.out.print(text);
    }

    public String getValidatedValue(String message) {
        write(message);
        while (true) {
            String userPrint = readLine();
            if (!userPrint.isEmpty() && !userPrint.isBlank()) {
                return userPrint;
            }
        }

    }
}