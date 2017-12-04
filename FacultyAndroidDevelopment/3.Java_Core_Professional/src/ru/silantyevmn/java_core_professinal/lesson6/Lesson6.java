package ru.silantyevmn.java_core_professinal.lesson6;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ru.silantyevmn.java_core_professinal.lesson6
 * Created by Михаил Силантьев on 04.12.2017.
 *
 * 1. Написать метод, которому в качестве аргумента передается не пустой одномерный целочисленный массив,
 * метод должен вернуть новый массив, который получен путем вытаскивания элементов из исходного массива,
 * идущих после последней четверки. Входной массив должен содержать хотя бы одну четверку, в противном случае в методе необходимо выбросить RuntimeException.

 Написать набор тестов для этого метода (варианта 3-4 входных данных)
 вх: [ 1 2 4 4 2 3 4 1 7 ] -> вых: [ 1 7 ]
 2. Написать метод, который проверяет что массив состоит только из чисел 1 и 4. Если в массиве нет хоть одной 4 или 1, то метод вернет false;

 Написать набор тестов для этого метода (варианта 3-4 входных данных)
 3. Создать небольшую БД (таблица: студенты; поля: id, фамилия, балл).
 Написать тесты для проверки того, что при работе с базой корректно добавляются, обновляются и читаются записи.
 Следует учесть что в базе есть заранее добавленные записи, и после проведения тестов эти записи не должны быть удалены/изменены/добавлены.
 */
public class Lesson6 {
    public static void main(String[] args) {
        //1
        System.out.println(metod1(new Integer[]{1,2,4,4,2,3,4,1,7},4));
        //2
        
    }

    public static <T extends Number> ArrayList metod1 (T[] arrs, T value){
        int index=-1;
        ArrayList<T> tempArrs=new ArrayList<>();
        for (int i = 0; i < arrs.length; i++) {
            if(value==arrs[i])
                index=i;
        }
        if(index<0) {
            throw new RuntimeException("В массиве нет " + value + "!");
        }
        else {
            for (int i = index+1,j=0; i < arrs.length; i++,j++) {
                tempArrs.add(arrs[i]);
            }
            return tempArrs;
        }

    }
}
