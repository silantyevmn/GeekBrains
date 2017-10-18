package JavaCore.Lesson5;

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
    private static final int SIZE = 10000000; //размер массива
    private static final int COUNT_THREAD = 4; // кол-во потоков
    public static int VALUE_DEFAULT_ARR=1; // заполнить массив 1
    public static float[] arr = new float[SIZE]; // исходный массив
    public static float[] singleTreadArr = new float[SIZE]; //массив после прохода в один поток
    public static float[] multiTreadArr = new float[SIZE]; // массив после прохода COUNT_THREAD потоков
    public static long singleTreadTime;
    public static long multiTreadTime;

    public static void main(String[] args) {
        init(VALUE_DEFAULT_ARR);
        System.out.println("итого один поток: "+singleTread());
        System.out.println("итого "+COUNT_THREAD+" потока:"+multiTread());
        System.out.printf("Производительность %.2f%%",(1-(double)multiTreadTime/(double)singleTreadTime)*100);
        //проверка массивов
        System.out.println("\nКонтрольная проверка-"+ Arrays.equals(singleTreadArr,multiTreadArr));
        System.out.println("Массив1 сумма:"+result(singleTreadArr));
        System.out.println("Массив2 сумма:"+result(multiTreadArr));
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
        int tempSizeArr=SIZE/COUNT_THREAD;
        long startTime = System.currentTimeMillis();
        long a=System.currentTimeMillis();
        float[][] floats=new float[COUNT_THREAD][tempSizeArr];
        for (int i = 0; i < floats.length; i++) {
            floats[i]=new float[tempSizeArr];
            System.arraycopy(arr, i*tempSizeArr, floats[i], 0, tempSizeArr);
        }
        print(a,"разбивка");

        a=System.currentTimeMillis();
        MyThread[] thread=new MyThread[COUNT_THREAD];
        for (int i = 0; i < thread.length; i++) {
            try {
            thread[i]=new MyThread("поток-"+i,floats[i],i*tempSizeArr);
            thread[i].start();
                thread[i].join();
                //print(a,"расчет "+i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        print(a,"расчет");

        a=System.currentTimeMillis();
        for (int i = 0; i < floats.length; i++) {
            if(i==0) System.arraycopy(thread[i].getArr(), 0, multiTreadArr, 0, tempSizeArr);
            else System.arraycopy(thread[i].getArr(), 0, multiTreadArr, tempSizeArr*i, tempSizeArr);
        }
        print(a,"склейка");
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
        System.out.printf(text+"-"+countTime+",");
    }
}
