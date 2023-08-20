package itmo.progg.managers_client.example;

import itmo.progg.managers_client.Client;
import itmo.progg.managers_client.Worker;

import java.io.EOFException;

public class Main {
    private static String host = "localhost";
    private static int port = 8817;
    public static void main(String[] args) {


        Client client = new Client(host, port);
        Worker worker = new Worker(client);
        try {
            worker.run();
        } catch (EOFException e) {
            System.out.println("Произошла ошибка" + e.getMessage());
        }

    }
}