package ru.silantyevmn.java_core_professinal.lesson4;

/**
 * ru.silantyevmn.java_core_professinal.lesson4
 * Created by Михаил Силантьев on 15.11.2017.
 */
public class MyThread extends Thread {
    private final Object monitor;
    private volatile static char currentChar='A';
    private char letter1;
    private char letter2;

    public MyThread(Object monitor,char letter1,char letter2) {
        this.monitor = monitor;
        this.letter1 = letter1;
        this.letter2 = letter2;
        start();
    }

    @Override
    public void run() {
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
