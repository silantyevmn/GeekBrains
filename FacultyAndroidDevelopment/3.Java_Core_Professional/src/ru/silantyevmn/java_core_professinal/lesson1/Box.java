package ru.silantyevmn.java_core_professinal.lesson1;

import java.util.ArrayList;

/**
 * ru.silantyevmn.java_core_professinal.lesson1
 * Created by Михаил Силантьев on 03.11.2017.
 */
public class  Box <T extends Fruit>{

    private ArrayList<T> arrayList = new ArrayList<>();
    private String name;

    public Box(String name) {
        this.name = name;
    }

    void add(T fruit){
        arrayList.add(fruit);
    }

    boolean addAll(Box<T> box){
        try {
            this.arrayList.addAll(box.arrayList);
            box.arrayList.clear();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    float getWeight(){
        float result=0;
        for (T fruit : arrayList) {
            result+=fruit.getWeight();
        }
        return result;
    }
    boolean compare(Box<?> box){
        return this.getWeight()==box.getWeight();
    }

    public String getName() {
        return name;
    }

    void print(){
        System.out.println( this.name+":"+Fruit.name +"-"+getWeight());
    }
}
