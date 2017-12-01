package ru.silantyevmn.java_core_professinal.lesson5;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Lock;

/**
 * ru.silantyevmn.java_core_professinal.lesson5
 * Created by Михаил Силантьев on 01.12.2017.
 */
class Car implements Runnable {
    private static int CARS_COUNT;
    private CyclicBarrier barrier;
    private Object monitor;
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

    public Car(Object monitor, CyclicBarrier barrier, Race race, int speed) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
        this.barrier = barrier;
        this.monitor = monitor;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));
            System.out.println(this.name + " готов");
            barrier.await(); //ждем пока все будут готовы
            barrier.await();// ждем пока не напишим гонка началась.
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }

        //проверка победителя
        synchronized (monitor) {
            if (MainClass.WIN_COUNT > 0) {
                System.out.println(this.name + " WIN " + " занял " + ++MainClass.WIN + " место.");
                MainClass.WIN_COUNT--; //уменьшпем места победителей
            }
        }
        try {
            barrier.await(); //ждем пока гонка закончится
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

}
