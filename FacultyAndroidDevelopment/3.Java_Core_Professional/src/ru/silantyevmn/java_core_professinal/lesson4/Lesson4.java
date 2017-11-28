package ru.silantyevmn.java_core_professinal.lesson4;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ru.silantyevmn.java_core_professinal.lesson4
 * Created by Михаил Силантьев on 27.11.2017.
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
    private volatile static char currentChar = 'A';
    public static DataOutputStream out = null;

    public static void main(String[] args) throws InterruptedException {
        //1
        Lesson4 monitor = new Lesson4();
        Thread t1 = new Thread(() -> {
            printToChar(monitor, 'A', 'B');
        });
        Thread t2 = new Thread(() -> {
            printToChar(monitor, 'B', 'C');
        });
        Thread t3 = new Thread(() -> {
            printToChar(monitor, 'C', 'A');
        });
        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();
        System.out.println("\n*********************");

        //2
        try {
            out = new DataOutputStream(new FileOutputStream("1.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Thread thWrite1 = new Thread(() -> {
            monitor.fileWriterText(out, "пишет поток №1 \n");
        });
        Thread thWrite2 = new Thread(() -> {
            monitor.fileWriterText(out, "пишет поток №2 \n");
        });
        Thread thWrite3 = new Thread(() -> {
            monitor.fileWriterText(out, "пишет поток №3 \n");
        });
        thWrite1.start();
        thWrite2.start();
        thWrite3.start();
        thWrite1.join();
        thWrite2.join();
        thWrite3.join();
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("*********************");

        //3
        Mfy mfy = new Mfy();
        mfy.scan("java",20);
        mfy.scan("java2",20);
        mfy.print("рецепты",15);
        mfy.print("рецепты2",5);
    }

    private void fileWriterText(DataOutputStream out, String text) {
        try {
            for (int i = 0; i < 10; i++) {
                out.writeUTF(text);
                System.out.print(text);
                Thread.sleep(20);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private static void printToChar(Object monitor, char letter1, char letter2) {
        synchronized (monitor) {
            try {
                for (int i = 0; i < 5; i++) {
                    while (currentChar != letter1) {
                        monitor.wait();
                    }
                    System.out.print(currentChar);
                    currentChar = letter2;
                    monitor.notifyAll();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}

class Mfy {
    private Object monitorPrint=new Object();
    private Object monitorScan=new Object();

    public void print(String text,int page) {
        new Thread(() -> {
            synchronized (monitorPrint) {
                for (int i = 1; i <= page; i++) {
                    System.out.println("Печать " + text + " страница-" + i);
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("************\n" + text + "-" + page + " стр. отпечатанно.");
            }
        }).start();

    }

    public void scan(String text,int page) {
            new Thread(() -> {
                synchronized (monitorScan) {
                    for (int i = 1; i <= page; i++) {
                        System.out.println("Сканер " +text+ " страница "+i);
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("************\n"+text+"-"+page+" стр. отсканированно.");
                }
            }).start();
    }
}


