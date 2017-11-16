package ru.silantyevmn.java_core_professinal.lesson1;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * ru.silantyevmn.java_core_professinal.lesson1
 * Created by Михаил Силантьев on 03.11.2017.
 */
public class Box<T extends Fruit> {

    private ArrayList<T> arrayList = new ArrayList<>();
    private String name;

    public Box(String name) {
        this.name = name;
    }

    void add(T... fruit) {
       arrayList.addAll(Arrays.asList(fruit));
//        for (int i = 0; i < fruit.length; i++) {
//            arrayList.add(fruit[i]);
//        }
    }

    boolean addAll(Box<T> box) {
        this.arrayList.addAll(box.arrayList);
        box.arrayList.clear();
        return true;
    }

    float getWeight() {
        float result = 0;
        for (T fruit : arrayList) {
            result += fruit.getWeight();
        }
        return result;
    }

    boolean compare(Box<?> box) {
        return Math.abs(this.getWeight()-box.getWeight())<0.000001;
    }

    public String getName() {
        return name;
    }

    void print() {
        System.out.println(this.name + ":" + Fruit.name + "-" + getWeight());
    }
}
