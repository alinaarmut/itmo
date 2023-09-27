package itmo.progg.managers_client.example;

import itmo.progg.managers_client.Client;
import itmo.progg.managers_client.CustomScanner;
import itmo.progg.managers_client.Worker;
import itmo.progg.managers_client.utility.Console;


public class Main {
    private static String host = "localhost";
    private static int port = 6023;
    private static Console console = new Console();
    public static void main(String[] args) {



        Client client = new Client(host, port, 5000, 5);
        CustomScanner scanner = new CustomScanner(System.in);
        Worker worker = new Worker(client, scanner, console);
        worker.run();

    }
}