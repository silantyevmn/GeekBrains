package ru.silantyevmn.java_core_professinal.lesson4;

/**
 * ru.silantyevmn.java_core_professinal.lesson4
 * Created by Михаил Силантьев on 15.11.2017.
 */
public class Mfy {
    public void print(){
        for (int i = 1; i < 20; i++) {
            System.out.print("Отпечатана "+i+",");
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        System.out.print("\nВсе страницы напечатаны.");
    }
    public void scan(){
        for (int i = 1; i < 20; i++) {
            System.out.print("Отсканированна "+i+",");
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        System.out.print("\nВсе страницы отсканированы.");
    }
}
