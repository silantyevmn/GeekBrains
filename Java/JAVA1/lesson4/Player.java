package Java1.lesson4;

import java.util.Scanner;

/**
 * Java Level 1. Lesson 4.
 *
 * @author Mikhail Silantev
 * @version dated 05.08.2017.
 */

public class Player {
    int x;
    int y;
    char bot;
    String name;
    Scanner scaner = new Scanner(System.in);

    public Player() {
        System.out.println("Добро пожаловать в игру Крестики-Нолики!");
        System.out.println("Введите Ваше имя : ");
        this.name = inputScannerString();
        setPlayerBot();
    }

    String inputScannerString() {
        String text;
        do {
            text = scaner.nextLine();
            if (text.length() == 0) System.out.println("Вы ничего не ввели, попробуйте еще раз!");
        } while (text.length() < 1);
        return text;
    }

    int inputScannerInt(int min, int max) {
        int tempInt = -1;
        do {
            try {
                String tempText = inputScannerString();
                tempInt = Integer.parseInt(tempText);
            } catch (Exception e) {
                System.out.println("Ошибка при вводе, попробуйте еще раз!");
            }
            if (tempInt < min || tempInt > max) {
                System.out.println("Вы ввели недопустимое значение!");
            }
        } while (tempInt < min || tempInt > max);
        return tempInt;
    }

    void setPlayerBot() {
        System.out.println("Здравствуйте, " + name + ".");
        System.out.println("За кого будете играть X или O, введите 1 или 0 :");
        bot = (inputScannerInt(0, 1) == 1) ? 'X' : 'O';
        System.out.println("Спасибо за выбор. Теперь ваша фигура на поле: " + bot);
    }

    void setPlayerPoint(int min, int max) {
        System.out.println("Введите координаты X Y");
        x = inputScannerInt(min, max);
        y = inputScannerInt(min, max);
    }
}
