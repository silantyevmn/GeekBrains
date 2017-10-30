package Java1.lesson2;

/**
 * Java Level 1. Lesson 2.
 *
 * @author Mikhail Silantev
 * @version dated 29.07.2017.
 */

/*Домашнее задание
        1 Задать целочисленный массив, состоящий из элементов 0 и 1. Например: [ 1, 1, 0, 0, 1, 0, 1, 1, 0, 0 ].
            С помощью цикла и условия заменить 0 на 1, 1 на 0;
        2 Задать пустой целочисленный массив размером 8. С помощью цикла заполнить его значениями 0 3 6 9 12 15 18 21;
        3 Задать массив [ 1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, 1 ] пройти по нему циклом, и числа меньшие 6 умножить на 2;
        4 Создать квадратный двумерный целочисленный массив (количество строк и столбцов одинаковое),
            и с помощью цикла(-ов) заполнить его диагональные элементы единицами;
        5 ** Задать одномерный массив и найти в нем минимальный и максимальный элементы (без помощи интернета);
        6 ** Написать метод, в который передается не пустой одномерный целочисленный массив,
            метод должен вернуть true если в массиве есть место, в котором сумма левой и правой части массива равны.
             Примеры: checkBalance([1, 1, 1, || 2, 1]) → true, checkBalance ([2, 1, 1, 2, 1]) → false,
             checkBalance ([10, || 10]) → true, граница показана символами ||, эти символы в массив не входят.
        7 **** Написать метод, которому на вход подается одномерный массив и число n (может быть положительным, или отрицательным),
         при этом метод должен сместить все элементы массива на n позиций. Для усложнения задачи нельзя пользоваться вспомогательными массивами.*/

public class Lesson2 {

    public static void main(String[] args) {
        job1();
        job2();
        job3();
        job4();
        job5();
        job6();
        job7();
    }

    private static void job1() {
        /*1 Задать целочисленный массив, состоящий из элементов 0 и 1. Например: [ 1, 1, 0, 0, 1, 0, 1, 1, 0, 0 ]."+
                "С помощью цикла и условия заменить 0 на 1, 1 на 0;*/
        int[] cells = {1, 1, 0, 0, 1, 0, 1, 1, 0, 0};
        for (int i = 0; i < cells.length; i++) {
            cells[i] = (cells[i] == 0) ? 1 : 0;
        }
        showCells(cells);
    }

    private static void job2() {
        //2 Задать пустой целочисленный массив размером 8. С помощью цикла заполнить его значениями 0 3 6 9 12 15 18 21;
        int[] cells = new int[8];
        for (int i = 0; i < cells.length; i++) {
            cells[i] = i * 3;
        }
        showCells(cells);
    }

    private static void job3() {
        //3 Задать массив [ 1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, 1 ] пройти по нему циклом, и числа меньшие 6 умножить на 2;
        int[] cells = {1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, 1};
        for (int i = 0; i < cells.length; i++) {
            if (cells[i] < 6) {
                cells[i] *= 2;
            }
        }
        showCells(cells);
    }

    private static void job4() {
        /*4 Создать квадратный двумерный целочисленный массив (количество строк и столбцов одинаковое),
                 и с помощью цикла(-ов) заполнить его диагональные элементы единицами;*/
        int[][] cells = new int[7][7];
        int n=cells.length;
        for (int i = 0; i < cells.length; i++) {
            System.out.println();
            n--;
            for (int j = 0; j < cells[i].length; j++) {
                if(i==j || j==n) {
                    cells[i][j] = 1;
                } else
                    cells[i][j]=0;
                System.out.print(cells[i][j] + " ");
            }
        }
        System.out.println();
    }

    private static void job5() {
        //5 ** Задать одномерный массив и найти в нем минимальный и максимальный элементы (без помощи интернета);
        int[] cells = {1, 2, 3, 4, 5, -8, 25, 9, 26};
        int tempMin = 0;
        int tempMax = 0;
        for (int i = 0; i < cells.length; i++) {
            if (tempMax <= cells[i]) {
                tempMax = cells[i];
            } else if (tempMin >= cells[i]) {
                tempMin = cells[i];
            }
        }
        System.out.println("Max : "+tempMax + "  Min: " + tempMin);
    }

    private static void job6() {
        /*6 ** Написать метод, в который передается не пустой одномерный целочисленный массив,
                метод должен вернуть true если в массиве есть место, в котором сумма левой и правой части массива равны.
        Примеры: checkBalance([1, 1, 1, || 2, 1]) → true, checkBalance ([2, 1, 1, 2, 1]) → false,
                checkBalance ([10, || 10]) → true, граница показана символами ||, эти символы в массив не входят.*/

        System.out.println(checkBalance(new int[]{1,1,1,2,1}));
        System.out.println(checkBalance(new int[]{2,1,1,2,1}));
        System.out.println(checkBalance(new int[]{10,10}));
    }

    private static void job7() {
        /*7 **** Написать метод, которому на вход подается одномерный массив и число n (может быть положительным, или отрицательным),
        при этом метод должен сместить все элементы массива на n позиций. Для усложнения задачи нельзя пользоваться вспомогательными массивами.*/
        toShiftArrayElement(new int[]{1,2,3,4,5,6,7,8,9,10}, -3);
        toShiftArrayElement(new int[]{1,2,3,4,5,6,7,8,9,10}, 1);
    }

    private static void toShiftArrayElement(int[] cells, int n) {
        int temp;
        if (n > 0) { // если больше 0 --> сдвигаем вправо
            for (int j = 0; j < n; j++) {
                for (int i = cells.length - 1; i > 0; i--) {
                    temp = cells[i];
                    cells[i] = cells[i - 1];
                    cells[i - 1] = temp;
                }
            }
        } else if (n < 0) { // если меньше 0 <-- сдвигаем влево
            for (int j = 0; j < (n * -1); j++) {
                for (int i = 0; i < cells.length - 1; i++) {
                    temp = cells[i];
                    cells[i] = cells[i + 1];
                    cells[i + 1] = temp;
                }
            }
        }
        showCells(cells);
    }

    private static boolean checkBalance(int[] cells) {
        int startCells = 0;
        int finishCells = cells.length;
        int sumLeft = 0;
        int sumRight = 0;
        for (int i = startCells; i < finishCells; i++) {
            if (i < finishCells / 2) {
                sumLeft += cells[i];
                sumRight += cells[finishCells - i - 1];
            }
        }
        if ((finishCells % 2) == 0) {
            if (sumLeft == sumRight) {
                return true;
            }
        } else {
            if ((sumLeft + cells[finishCells / 2]) == sumRight) {
                return true;
            }
            if (sumLeft == (sumRight + cells[finishCells / 2])) {
                return true;
            }
        }
        return false;
    }

    private static void showCells(int[] cells) {
        System.out.print("{ ");
        for (int i = 0; i < cells.length; i++) {
            System.out.print(cells[i] + " ");
        }
        System.out.print(" }\n");
    }
}
