package itmo.prog;

import itmo.prog.managers.Worker;

import java.util.Scanner;

/**
 * @author alinaarmut
 * Главный класс
 */
public class Lab6 {
    public static void main(String[] args) {

        Worker worker = new Worker();
        worker.cmdAdd();
        worker.run(args);
    }
}















