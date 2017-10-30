package Java1.lesson1;

import java.util.Scanner;

/**
 * Created by Михаил Силантьев on 25.07.2017.
 */
public class Lesson1 {
    public static void main(String[] args) {
        /* 2. Создать переменные всех пройденных типов данных, и инициализировать их значения; */
        // целочисленные переменные
        byte by = 8;
        short s = 16;
        int i = 32;
        long lg = 64;
        // с плавающей точкой
        float f = 32.000000f;
        double db = 64.00000;

        /*3. Написать метод вычисляющий выражение a * (b + (c / d)) и возвращающий результат,
        где a, b, c, d – входные параметры этого метода;*/
        int a = 1;
        int b = 2;
        int c = 3;
        int d = 4;
        System.out.println(calculation(a, b, c, d));

        /*4. Написать метод, принимающий на вход два числа, и проверяющий что их сумма лежит в пределах от 10 до 20(включительно),
         если да – вернуть true, в противном случае – false; */
        int a1 = 5;
        int a2 = 10;
        System.out.println(isSumCalculation(a1, a2));

        /*5. Написать метод, которому в качестве параметра передается целое число, метод должен напечатать
        в консоль положительное ли число передали, или отрицательное; Замечание: ноль считаем положительным числом.*/
        int a3 = 0;
        calculateTheNumber(a3);

        /*6. Написать метод, которому в качестве параметра передается целое число,
         метод должен вернуть true, если число отрицательное;*/
        int a4 = 0;
        boolean b2 = isCalculateTheNumber(a3);

        /*7. Написать метод, которому в качестве параметра передается строка,
         обозначающая имя, метод должен вывести в консоль сообщение «Привет, указанное_имя!»;*/
        String name = "Mikhail";
        showStringName(name);

        /*8. * Написать метод, который определяет является ли год високосным, и выводит сообщение в консоль.
        Каждый 4-й год является високосным, кроме каждого 100-го, при этом каждый 400-й – високосный.*/
        Scanner scan = new Scanner(System.in);
        System.out.print("Сейчас попробуем расчитать год ,високосный или обычный. Просто введите его : ");
        int year = Integer.parseInt(scan.nextLine());
        showYear(year);
    }

    static void showYear(int year) {
        if (year % 4 != 0 || (year % 100 == 0 && year % 400 != 0)) {
            System.out.println(year + " год - обычный.");
        } else {
            System.out.println(year + " год - високосный.");
        }
    }

    static void showStringName(String name) {
        System.out.println("Привет " + name + "!");
    }

    static boolean isCalculateTheNumber(int a3) {
        if (a3 < 0) {
            return true;
        }
        return false;
    }

    static void calculateTheNumber(int a3) {
        String s = "Отрицательное";
        if (a3 >= 0) {
            s = "Положительное";
        }
        System.out.println("Ваше число " + a3 + " : " + s);
    }

    static boolean isSumCalculation(int a1, int a2) {
        int sum = a1 + a2;
        if (sum >= 10 && sum <= 20) {
            return true;
        }
        return false;
    }

    static double calculation(int a, int b, int c, int d) {
        if(d==0){
            System.out.println(" На ноль делить нельзя!");
            return 0;
        }
        double cals = a * (b + (c / d));
        return cals;
    }
}
