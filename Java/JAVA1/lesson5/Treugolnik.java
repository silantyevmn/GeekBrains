package Java1.lesson5;

import java.util.Scanner;

/**
 * Java Java1.lesson5
 *
 * @author Mikhail Silantev
 * @version date 08.08.2017.
 */
public class Treugolnik {

    public static void main(String[] args) {
        System.out.println("Введите высоту треугольника(строк) :");
        Scanner sc=new Scanner(System.in);
        int lines=sc.nextInt();

        for (int i = 0; i < lines; i++) {
            for (int j = 1; j < lines-i; j++) {
                System.out.print(" ");

            }
            for (int j = lines-i*2; j <= lines; j++) {
                System.out.print("^");
            }
            System.out.println();
        }
    }
}
