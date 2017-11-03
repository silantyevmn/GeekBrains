package ru.silantyevmn.java_core_professinal.lesson1;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * ru.silantyevmn.java_core_professinal
 * Created by Михаил Силантьев on 03.11.2017.
 *
 * 1. Написать метод, который меняет два элемента массива местами.(массив может быть любого ссылочного типа);
 * <p>
 * 2. Написать метод, который преобразует массив в ArrayList;
 * <p>
 * 3. Большая задача:
 * <p>
 * Есть классы Fruit -> Apple, Orange;(больше фруктов не надо)
 * Класс Box в который можно складывать фрукты, коробки условно сортируются по типу фрукта, поэтому в одну коробку нельзя сложить и яблоки, и апельсины;
 * Для хранения фруктов внутри коробки можете использовать ArrayList;
 * Сделать метод getWeight() который высчитывает вес коробки, зная количество фруктов и вес одного фрукта(вес яблока - 1.0f, апельсина - 1.5f,
 * не важно в каких это единицах);
 * Внутри класса коробка сделать метод compare, который позволяет сравнить текущую коробку с той, которую подадут в compare в качестве параметра,
 * true - если их веса равны, false в противном случае(коробки с яблоками мы можем сравнивать с коробками с апельсинами);
 * Написать метод, который позволяет пересыпать фрукты из текущей коробки в другую коробку
 * (помним про сортировку фруктов, нельзя яблоки высыпать в коробку с апельсинами),
 * соответственно в текущей коробке фруктов не остается, а в другую перекидываются объекты, которые были в этой коробке;
 * Не забываем про метод добавления фрукта в коробку.
 */
public class Lesson1 {
    private final static String BOX_COMPARE = "Сравнение коробок %s и %s, результат: %s\n";
    private final static String BOX_POOR = "Пересыпаем из %s в %s ,результат: %s\n";

    public static void main(String[] args) {
        String[] arr = {"10", "20", "30","40","50"};
        //1.
        System.out.println(Arrays.toString(arr));
        swapsArr(arr); // меняем массив местами

        //2.
        ArrayList newArr=convertToArrayList(arr); //преобразует массив в ArrayList
        System.out.println(newArr);

        //3.
        Box <Apple> box1=new Box("box1");
        box1.add(new Apple());
        box1.add(new Apple());
        box1.add(new Apple());
        box1.print();

        Box <Orange> box2=new Box("box2");
        box2.add(new Orange());
        box2.add(new Orange());
        box2.print();

        Box<Apple> box3=new Box<>("box3");
        box3.add(new Apple());
        box3.add(new Apple());
        box3.print();
        // сравнение про весу фруктов
        System.out.printf(BOX_COMPARE,box1.getName(),box2.getName(),box1.compare(box2));
        System.out.printf(BOX_COMPARE,box2.getName(),box3.getName(),box2.compare(box3));
        //пересыпаем коробку c яблоками
        System.out.printf(BOX_POOR,box1.getName(),box3.getName(),box1.addAll(box3));
        box1.print();
        box3.print();

        //box2.addAll(box1);
    }

    private static <T> ArrayList convertToArrayList(T[] arr) {
        ArrayList<T> newArr=new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            newArr.add(arr[i]);
        }
        return newArr;
    }

    private static <T> void swapsArr (T[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = i+1; j < arr.length; j++) {
                T temp = arr[j];
                arr[j] = arr[i];
                arr[i] = temp;
            }
        }
    }

}
