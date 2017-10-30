package Java1.lesson7;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Java Java1.lesson7
 *
 * @author Mikhail Silantev
 * @version date 16.08.2017.
 */
public class Lesson7 {
    public static final int AMOUNT_CATS = 10;
    public static final int AMOUNT_FOOD = 90;
    public static Random ran = new Random();
    public static Scanner sc = new Scanner(System.in);
    public static ArrayList<Cat> cats;
    public static int countCatisSatiety = AMOUNT_CATS;

    public static void main(String[] args) {
        Plate plate = new Plate(AMOUNT_FOOD);
        cats = new ArrayList<Cat>(AMOUNT_CATS);
        initCats(AMOUNT_CATS);
        boolean isGameOver = false;
        do {
            eatCats(plate);
            plate.info();
            isGameOver = eatAdd(plate);
        } while (!isGameOver);
        System.out.println("До свидания!!!");
    }

    private static boolean eatAdd(Plate plate) {
        if (countCatisSatiety == 0) return true;
        System.out.println("Хотите еще накормить кошек?\n " + "Осталось :" + countCatisSatiety + " Cat не накормленны." + " (1 накормить, 0 выход):");
        if (getInputNumber(0, 1) == 1) {
            //добавляем еду в тарелку
            System.out.println("Введите количество еды (от 1 до 100)");
            plate.info();
            plate.addFoot(getInputNumber(1, 100));
            return false;
        } else {
            return true;
        }
    }

    private static int getInputNumber(int min, int max) {
        int number = -1;
        do {
            String text = sc.nextLine();
            try {
                number = Integer.parseInt(text);
            } catch (Exception e) {
                System.out.println("Введено неверное значение!");
            }
            if (number < min || number > max) {
                System.out.println(" Попробуйте еще раз!");
            }
        } while (number < min || number > max);
        return number;
    }

    private static void eatCats(Plate plate) {
        for (Cat cat : cats) {
            cat.eat(plate);
            System.out.println(cat);
        }
    }

    private static void initCats(int length) {
        for (int i = 0; i < length; i++) {
            Cat c = new Cat("Cat-" + (i + 1), getRandom(1, 10), getRandom(8, 15), false);
            cats.add(c);
        }
    }

    private static int getRandom(int min, int max) {
        return ran.nextInt(max - min) + min;
    }
}

class Cat {
    private String name;
    private int age;
    private int appetite;
    private boolean isSatiety;

    public Cat(String name, int age, int appetite, boolean isSatiety) {
        this.name = name;
        this.age = age;
        this.appetite = appetite;
        this.isSatiety = isSatiety;
    }

    void eat(Plate plate) {
        if (!isSatiety) {
            this.isSatiety = plate.decreaseFood(appetite);
            if (isSatiety) {
                Lesson7.countCatisSatiety--;
            }
        }
    }

    @Override
    public String toString() {
        return "Имя='" + name + '\'' +
                ", возраст=" + age +
                ", аппетит=" + appetite +
                ", сытость=" + ((isSatiety) ? "Сытый" : "Голодный");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAppetite() {
        return appetite;
    }

    public void setAppetite(int appetite) {
        this.appetite = appetite;
    }

    public boolean isSatiety() {
        return isSatiety;
    }

    public void setSatiety(boolean satiety) {
        isSatiety = satiety;
    }
}

class Plate {
    private int food;

    public Plate(int food) {
        this.food = food;
    }

    public boolean decreaseFood(int n) {
        if (food >= n) {
            food -= n;
            return true;
        } else return false;
    }

    void info() {
        System.out.println("В тарелке :" + ((food == 0) ? "нет корма." : food + " грамм корма."));
    }

    void addFoot(int n) {
        food += n;
        info();
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }

}
