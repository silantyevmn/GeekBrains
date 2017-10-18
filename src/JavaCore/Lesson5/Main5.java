package JavaCore.Lesson5;

import JavaCore.Lesson1.Main;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * JavaCore.Lesson5
 * Created by Михаил Силантьев on 18.10.2017.
 * 1. Необходимо написать два метода, которые делают следующее:

 1) Создают одномерный длинный массив, например:
 static final int size = 10000000;
 static final int h = size / 2;
 float[] arr = new float[size];
 2) Заполняют этот массив единицами;
 3) Засекают время выполнения: long a = System.currentTimeMillis();
 4) Проходят по всему массиву и для каждой ячейки считают новое значение по формуле:
 arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
 5) Проверяется время окончания метода System.currentTimeMillis();
 6) В консоль выводится время работы: System.out.println(System.currentTimeMillis() - a);
 Отличие первого метода от второго:
 Первый просто бежит по массиву и вычисляет значения.
 Второй разбивает массив на два массива, в двух потоках высчитывает новые значения и потом склеивает эти массивы обратно в один.
 Пример деления одного массива на два:
 System.arraycopy(arr, 0, a1, 0, h);
 System.arraycopy(arr, h, a2, 0, h);
 Пример обратной склейки:
 System.arraycopy(a1, 0, arr, 0, h);
 System.arraycopy(a2, 0, arr, h, h);
 Примечание:
 System.arraycopy() – копирует данные из одного массива в другой:
 System.arraycopy(массив-источник, откуда начинаем брать данные из массива-источника, массив-назначение, откуда начинаем записывать данные в массив-назначение, сколько ячеек копируем)
 По замерам времени:
 Для первого метода надо считать время только на цикл расчета:
 for (int i = 0; i < size; i++) {
 arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
 }
 Для второго метода замеряете время разбивки массива на 2, просчета каждого из двух массивов и склейки.
 Для второго метода необходимо учесть смещение i в формуле заполнения ячейки, для второй половины массива.
 ** Создать универсальный метод, который делит просчет массива на столько потоков, сколько ему было передано.
 */
public class Main5 {
    private static final int SIZE = 100;
    private static final int SIZE_SMALL = SIZE / 2;
    public static volatile float[] arr = new float[SIZE];
    public static float[] singleTreadArr = new float[SIZE];
    public static float[] multiTreadArr = new float[SIZE];
    public static long singleTreadTime;
    public static long multiTreadTime;

    public static void main(String[] args) {
        init(1);
        System.out.println("метод1 итого: "+singleTread());
        System.out.println("метод2 итого:"+multiTread());

        //проверка массивов
        System.out.println("\nКонтрольная проверка"+ Arrays.equals(singleTreadArr,multiTreadArr));
        System.out.println("Массив1-"+result(singleTreadArr));
        System.out.println("Массив2-"+result(multiTreadArr));
    }



    public static long singleTread(){
        long startTime = System.currentTimeMillis();
        System.arraycopy(arr, 0, singleTreadArr, 0, SIZE);
        MyThread t=new MyThread("метод1 ",singleTreadArr,0);
        t.metod(0);
        singleTreadTime=System.currentTimeMillis()-startTime;
        singleTreadArr=t.getArr();
        return singleTreadTime;
    }

    public static long multiTread() {
        long startTime = System.currentTimeMillis();
        float[] a1 = new float[SIZE_SMALL];
        float[] a2 = new float[SIZE_SMALL];

        long a=System.currentTimeMillis();
        System.arraycopy(arr, 0, a1, 0, SIZE_SMALL);
        System.arraycopy(arr, SIZE_SMALL, a2, 0, SIZE_SMALL);
        print(a,"разбивка массивов");

        a=System.currentTimeMillis();
        MyThread t1=new MyThread("метод2-поток1 ",a1,0);
        MyThread t2=new MyThread("метод2-поток2 ",a2,SIZE_SMALL);
        t1.start();
        t2.start();
        try {
            t1.join();
            print(a,"расчет 1 массив");
            a=System.currentTimeMillis();
            t2.join();
            print(a,"расчет 2 массив");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        a=System.currentTimeMillis();
        System.arraycopy(t1.getArr(), 0, multiTreadArr, 0, SIZE_SMALL);
        System.arraycopy(t2.getArr(), 0, multiTreadArr, SIZE_SMALL, SIZE_SMALL);
        print(a,"склейка массивов");

        multiTreadTime=System.currentTimeMillis()-startTime;
        return multiTreadTime;
    }

    public static void init(int value) {
        for (int i = 0; i < SIZE; i++) {
            arr[i]=value;
        }
    }

    public static float result(float[] arr){
        float sum=0;
        for (int i = 0; i < arr.length; i++) {
            sum+=arr[i];
        }
        return sum;
    }

    public static void print(long a,String text){
        long countTime=System.currentTimeMillis()-a;
        System.out.printf(text+" "+countTime+" м.сек. ");
    }
}
