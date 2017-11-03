package ru.silantyevmn.java_core_professinal.lesson1;

/**
 * ru.silantyevmn.java_core_professinal.lesson1
 * Created by Михаил Силантьев on 03.11.2017.
 */
public class Fruit {
    private float weight;
    public static String name;

    public String getName() {
        return name;
    }

    public float getWeight() {
        return weight;
    }

    public Fruit(float weight, String name) {
        this.weight = weight;
        this.name=name;

    }
}


