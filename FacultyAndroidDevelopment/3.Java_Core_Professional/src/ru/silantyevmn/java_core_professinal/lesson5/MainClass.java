package ru.silantyevmn.java_core_professinal.lesson5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ru.silantyevmn.java_core_professinal.lesson5
 * Created by Михаил Силантьев on 30.11.2017.
 * Что примерно должно получиться
 * <p>
 * ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!
 * Участник #2 готовится
 * Участник #1 готовится
 * Участник #4 готовится
 * Участник #3 готовится
 * Участник #2 готов
 * Участник #4 готов
 * Участник #1 готов
 * Участник #3 готов
 * ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!
 * Участник #2 начал этап: Дорога 60 метров
 * Участник #4 начал этап: Дорога 60 метров
 * Участник #3 начал этап: Дорога 60 метров
 * Участник #1 начал этап: Дорога 60 метров
 * Участник #1 закончил этап: Дорога 60 метров
 * Участник #3 закончил этап: Дорога 60 метров
 * Участник #3 готовится к этапу(ждет): Тоннель 80 метров
 * Участник #1 готовится к этапу(ждет): Тоннель 80 метров
 * Участник #1 начал этап: Тоннель 80 метров
 * Участник #3 начал этап: Тоннель 80 метров
 * Участник #4 закончил этап: Дорога 60 метров
 * Участник #4 готовится к этапу(ждет): Тоннель 80 метров
 * Участник #2 закончил этап: Дорога 60 метров
 * Участник #2 готовится к этапу(ждет): Тоннель 80 метров
 * Участник #3 закончил этап: Тоннель 80 метров
 * Участник #1 закончил этап: Тоннель 80 метров
 * Участник #2 начал этап: Тоннель 80 метров
 * Участник #4 начал этап: Тоннель 80 метров
 * Участник #3 начал этап: Дорога 40 метров
 * Участник #1 начал этап: Дорога 40 метров
 * Участник #3 закончил этап: Дорога 40 метров
 * Участник #3 - WIN
 * Участник #1 закончил этап: Дорога 40 метров
 * Участник #4 закончил этап: Тоннель 80 метров
 * Участник #4 начал этап: Дорога 40 метров
 * Участник #2 закончил этап: Тоннель 80 метров
 * Участник #2 начал этап: Дорога 40 метров
 * Участник #2 закончил этап: Дорога 40 метров
 * Участник #4 закончил этап: Дорога 40 метров
 * ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!
 */
public class MainClass {
    public static final int CARS_COUNT = 4;
    public static CountDownLatch start = new CountDownLatch(CARS_COUNT + 1); //количество действий до вывода гонка началась!
    public static final Semaphore SEMAPHORE_TUNNEL = new Semaphore(CARS_COUNT / 2); //количество машин проезжающих по тунелю
    public static Lock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10));
            cars[i].setStart(start);
            cars[i].setLock(lock);
        }
        Thread thread[]=new Thread[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            thread[i]=new Thread(cars[i]);
            thread[i].start();
        }
        while (start.getCount() != 1) ; //пока все учасники не будут готовы, гонка не начнется
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
        start.countDown(); // разрешаем гонку

        for (int i = 0; i < cars.length; i++) {
            thread[i].join();
        }
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
    }
}

class Car implements Runnable {
    private static int CARS_COUNT;
    private CountDownLatch start; //готовность к старту
    private Lock win; //победитель
    private Race race;
    private int speed;
    private String name;

    static {
        CARS_COUNT = 0;
    }


    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Car(Race race, int speed) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));
            System.out.println(this.name + " готов");
            start.countDown(); //готовность -1
            start.await(); // пока все не будут готовы, дальше не пойдем
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }
        //проверка победителя
        if (win.tryLock()) {
            win.lock();
            System.out.println(this.name + " WIN ");
        }

    }

    public void setStart(CountDownLatch start) {
        this.start=start;
    }

    public void setLock(Lock lock) {
        this.win = lock;
    }

}

abstract class Stage {
    protected int length;
    protected String description;

    public String getDescription() {
        return description;
    }

    public abstract void go(Car c);
}

class Road extends Stage {
    public Road(int length) {
        this.length = length;
        this.description = "Дорога " + length + " метров";
    }

    @Override
    public void go(Car c) {
        try {
            System.out.println(c.getName() + " начал этап: " + description);
            Thread.sleep(length / c.getSpeed() * 1000);
            System.out.println(c.getName() + " закончил этап: " + description);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Tunnel extends Stage {
    public Tunnel() {
        this.length = 80;
        this.description = "Тоннель " + length + " метров";
    }

    @Override
    public void go(Car c) {
        try {
            try {
                System.out.println(c.getName() + " готовится к этапу(ждет): " + description);
                MainClass.SEMAPHORE_TUNNEL.acquire(); // въезд в туннель заблокирует, пока семафор не разрешит доступ
                System.out.println(c.getName() + " начал этап: " + description);
                Thread.sleep(length / c.getSpeed() * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(c.getName() + " закончил этап: " + description);
                MainClass.SEMAPHORE_TUNNEL.release();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Race {
    private ArrayList<Stage> stages;

    public ArrayList<Stage> getStages() {
        return stages;
    }

    public Race(Stage... stages) {
        this.stages = new ArrayList<>(Arrays.asList(stages));
    }
}

