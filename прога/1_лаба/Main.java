import java.util.Random;
public class Main {
    public static void main(String[] args) {
        int[] a = new int[14];
        int h =5;
        float[] x = new float[18];
        float max = -8.0f;
        float min = 2.0f;
        Random random = new Random();
        double [][] a1 = new double[14][18]; // заполняем первый массив
        for ( int i = 0 ; i < a.length; i++) {
            a[i]=h; h++; } // заполняем второй массив
        for (int i = 0; i < x.length; i++) {
            x[i] = random.nextFloat()*(max-min)+min; } for (int i = 0; i < 14; i++) {
            if (a[i] == 7) { for (int j = 0; j < 18; j++) {
                a1[i][j] = (Math.asin(1 / Math.exp(Math.abs((x[j])))));

            }
            } else if (a[i] == 10 || a[i] == 12 || a[i] == 13 || a[i] == 14 || a[i] == 15 || a[i] == 18 || a[i] == 19) {
                for (int j = 0; j < 18; j++) {
                    a1[i][j] = Math.cos(Math.pow((Math.pow(x[j], 1d/ 3)), 1d/ 3));
                }
            } else {
                for (int j = 0; j < 18; j++) {
                    a1[i][j] = Math.pow((2d/ 3 / Math.log(Math.pow(Math.cos(Math.pow(Math.tan(x[j]), (Math.pow(x[j] / 4, 3) - (1d/ 2)) / Math.cos(x[j]))), 2))), 2);
                }
            }

        }
        for (int i = 0; i < 14;i++) {
            for (int j = 0; j < 18; j++) {
                System.out.printf("%.2f", a1[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}