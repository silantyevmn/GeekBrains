package ru.silantyevmn.java_core_professinal.lesson5;

/**
 * ru.silantyevmn.java_core_professinal.lesson5
 * Created by Михаил Силантьев on 01.12.2017.
 */
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
