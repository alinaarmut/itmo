package itmo.progg.managers_client;

import java.io.InputStream;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CustomScanner {
    private Scanner scanner;
    private boolean isEmptyLine = false;

    public CustomScanner(InputStream inputStream) {
        this.scanner = new Scanner(inputStream);
    }

    public String nextLine() {
        System.out.print("$ ");
        return scanner.nextLine();
    }



    public int nextInt() {
        System.out.print("$ ");
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Неправильный ввод константы. Введите число:");
            scanner.nextLine();
            return nextInt();
        }
    }

    public long nextLong() {
        System.out.print("$ ");
        try {
            return scanner.nextLong();
        } catch (InputMismatchException e) {
            System.out.println("Неправильный ввод константы. Введите число:");
            scanner.nextLine();
            return nextLong();
        }
    }
    public String next() {
        System.out.print("$ ");
        return scanner.next();
    }


    public float nextFloat() {
        System.out.print("$ ");
        try {
            return scanner.nextFloat();
        } catch (InputMismatchException e) {
            System.out.println("Неправильный ввод константы. Введите число:");
            scanner.nextLine();
            return nextFloat();
        }
    }
    public boolean hasNextDouble() {
        System.out.print("$ ");
        return scanner.hasNextDouble();
    }
    public boolean hasNextInt() {
        System.out.print("$ ");
        return scanner.hasNextInt();
    }

    public double nextDouble() {
        System.out.print("$ ");
        return scanner.nextDouble();
    }
    public String nextLines() {
        if (!isEmptyLine) {
            System.out.println("$");
        }
        String line = scanner.nextLine();
        isEmptyLine = line.isEmpty();
        return line;
    }



}
