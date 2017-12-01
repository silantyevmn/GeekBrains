package ru.silantyevmn.java_core_professinal.lesson5;

/**
 * ru.silantyevmn.java_core_professinal.lesson5
 * Created by Михаил Силантьев on 01.12.2017.
 */
abstract class Stage {
    protected int length;
    protected String description;

    public String getDescription() {
        return description;
    }

    public abstract void go(Car c);
}
