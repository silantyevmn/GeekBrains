package ru.silantyevmn.java_core_professinal.lesson4;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * ru.silantyevmn.java_core_professinal.lesson4
 * Created by Михаил Силантьев on 15.11.2017.
 * <p>
 * 1. Создать три потока, каждый из которых выводит определенную букву (A, B и C) 5 раз, порядок должен быть именно ABСABСABС.
 * Используйте wait/notify/notifyAll.
 * <p>
 * 2. Написать совсем небольшой метод, в котором 3 потока построчно пишут данные в файл (штук по 10 записей, с периодом в 20 мс)
 * <p>
 * 3. Написать класс МФУ, на котором возможны одновременная печать и сканирование документов,
 * при этом нельзя одновременно печатать два документа или сканировать (при печати в консоль выводится сообщения "отпечатано 1, 2, 3,... страницы",
 * при сканировании тоже самое только "отсканировано...", вывод в консоль все также с периодом в 50 мс.)
 */
public class Lesson4 {
    public static void main(String[] args) {
        //1
        Object monitor = new Object();
        MyThread t1 = new MyThread(monitor, 'A', 'B');
        MyThread t2 = new MyThread(monitor, 'B', 'C');
        MyThread t3 = new MyThread(monitor, 'C', 'A');
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //2
        System.out.println();
        Object monitor2 = new Object();
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File("1.txt"));
            FileWriter f1 = new FileWriter(monitor2, out, 'a');
            f1.join();
            FileWriter f2 = new FileWriter(monitor2, out, 'b');
            f2.join();
            FileWriter f3 = new FileWriter(monitor2, out, 'c');
            f3.join();
            out.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //3
        System.out.println();
        Mfy mfy = new Mfy();
        new Thread(()-> {
            mfy.print();
        }).start();
        new Thread(()-> {
            mfy.scan();
        }).start();
    }
}
