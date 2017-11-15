package ru.silantyevmn.java_core_professinal.lesson4;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * ru.silantyevmn.java_core_professinal.lesson4
 * Created by Михаил Силантьев on 15.11.2017.
 */
public class FileWriter extends Thread {
    private final Object monitor;
    private FileOutputStream out;
    private char currentChar;

    public FileWriter(Object monitor, FileOutputStream out,char currentChar) {
        this.monitor = monitor;
        this.out=out;
        this.currentChar=currentChar;
        start();
    }

    @Override
    public void run() {
        synchronized (monitor) {
            try{
                for (int i = 0; i < 10; i++) {
                    out.write(currentChar);
                    System.out.print(currentChar);
                }
                out.flush();
                Thread.sleep(20);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
