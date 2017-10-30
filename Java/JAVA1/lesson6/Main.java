package Java1.lesson6;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Random;

/**
 * Java Java1.lesson6
 *
 * @author Mikhail Silantev
 * @version date 12.08.2017.
 */

/* 1. Создать классы Собака и Кот с наследованием от класса Животное.
      2. Животные могут выполнять действия: бежать, плыть, перепрыгивать препятствие.
        В качестве параметра каждому методу передается величина, означающая или длину препятствия (для бега и плавания), или высоту (для прыжков).
      3. У каждого животного есть ограничения на действия (бег: кот 200 м., собака 500 м.;
        прыжок: кот 2 м., собака 0.5 м.; плавание: кот не умеет плавать, собака 10 м.).
      4. При попытке животного выполнить одно из этих действий, оно должно сообщить результат в консоль. (Например, dog1.run(150); -> результат: run: true)
      5. * Добавить животным разброс в ограничениях. То есть у одной собаки ограничение на бег может быть 400 м., у другой 600 м. */

public class Main {
    public static ArrayList<Animals> animals=new ArrayList<Animals>();
    public static Random ran = new Random();

    public static void main(String[] args) {
        init(true,3);
        init(false,3);
        System.out.println(animals);
        start(250,0.9,13);// запускаем олимпиаду
    }

    private static void start(double run,double jump,double swim) {
        System.out.println("\nОлимпиада начинается...");
        for (Animals animal : animals) {
            animal.run(run);
            animal.jump(jump);
            animal.swim(swim);
        }
    }

    public static void init(boolean isCat,int count){
        Animals an;
        for (int i = 0; i < count; i++) {
            if (isCat) {
                an = new Cat("Мурка-"+(i+1), (int) getRandom(2, 10), getRandom(200, 250), getRandom(1.5, 2), false, 0);
            } else
                an = new Dog("Шарик-"+(i+1), (int) getRandom(2, 10), getRandom(400, 500), getRandom(0.5, 1), true, getRandom(9,15));
            animals.add(an);
        }
    }

    public static double getRandom(double min, double max) {
        return ran.nextDouble() * (max - min) + min;
    }
}

abstract class Animals {
    private String name;
    private int age;
    private double runLeng; //бег
    private double jumpLeng; // прыжок
    private double swimLeng; //плаванье
    private boolean isSwim;//умеет плавать?

    abstract void run(double leng);

    abstract void jump(double leng);

    abstract void swim(double leng);

    @Override
    public String toString() {
        NumberFormat nf = new DecimalFormat("#.#");
        return "\n"+getClass().getSimpleName() +
                " : " + getName() +
                " возраст: " + getAge() +
                " скорость бега: " + nf.format(getRunLeng()) +
                " высота прыжка: " + nf.format(getJumpLeng()) +
                " скорость плавания: " + nf.format(getSwimLeng()) +
                " умение плавать :" + (isSwim() ? "да." : "нет.");
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

    public double getRunLeng() {
        return runLeng;
    }

    public void setRunLeng(double runLeng) {
        this.runLeng = runLeng;
    }

    public double getJumpLeng() {
        return jumpLeng;
    }

    public void setJumpLeng(double jumpLeng) {
        this.jumpLeng = jumpLeng;
    }

    public double getSwimLeng() {
        return swimLeng;
    }

    public void setSwimLeng(double swimLeng) {
        this.swimLeng = swimLeng;
    }

    public boolean isSwim() {
        return isSwim;
    }

    public void setSwim(boolean swim) {
        isSwim = swim;
    }
}

class Cat extends Animals {
    public Cat(String name, int age, double running, double jumping, boolean isSwim, double swimLeng) {
        setName(name);
        setAge(age);
        setRunLeng(running);
        setJumpLeng(jumping);
        setSwim(isSwim);
        setSwimLeng(swimLeng);
    }

    @Override
    void run(double leng) {
        System.out.printf("\nКошка - %s дальность бега %.1f м участок %.1f м %s",getName(),getRunLeng(),leng,(getRunLeng()>=leng)? "преодолела":"не преодолела");
    }

    @Override
    void jump(double leng) {
        System.out.printf("\nКошка - %s высота прыжка %.1f м прыжок %.1f м %s",getName(),getJumpLeng(),leng,(getJumpLeng()>=leng)? "преодолела":"не преодолела");
    }

    @Override
    void swim(double leng) {
        System.out.printf("\nКошка - %s плавать не умеет.",getName());
    }
}

class Dog extends Animals {
    public Dog(String name, int age, double running, double jumping, boolean isSwim, double swimLeng) {
        setName(name);
        setAge(age);
        setRunLeng(running);
        setJumpLeng(jumping);
        setSwim(isSwim);
        setSwimLeng(swimLeng);
    }

    @Override
    void run(double leng) {
        System.out.printf("\nСобака - %s дальность бега %.1f м участок %.1f м %s",getName(),getRunLeng(),leng,(getRunLeng()>=leng)? "преодолел":"не преодолел");
    }

    @Override
    void jump(double leng) {
        System.out.printf("\nСобака - %s высота прыжка %.1f м прыжок %.1f м %s",getName(),getJumpLeng(),leng,(getJumpLeng()>=leng)? "преодолел":"не преодолел");
    }

    @Override
    void swim(double leng) {
        System.out.printf("\nСобака - %s дальность плавания %.1f м участок %.1f м %s",getName(),getSwimLeng(),leng,(getSwimLeng()>=leng)? "преодолел":"не преодолел");
    }
}
