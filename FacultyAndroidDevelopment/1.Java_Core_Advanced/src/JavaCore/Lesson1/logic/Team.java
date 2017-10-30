package JavaCore.Lesson1.logic;

import JavaCore.Lesson1.animals.*;
import java.util.Random;

/**
 * JavaCore.Lesson1
 * Created by Михаил Силантьев on 04.10.2017.
 */
public class Team{
    Animal[] animals;
    String name;
    Random ran=new Random();

    public Team(String name,int size) {
        this.name = name;
        this.animals=new Animal[size];
        init();
    }

    void init(){
        for (int i = 0; i < animals.length; i++) {
            if(ran.nextBoolean())  animals[i]=new Cat("Barsik"+(i+1),getRandom(800,1000),getRandom(3,5),0);
            else animals[i]=new Dog("Sharik"+(i+1),getRandom(1800,2000),getRandom(1,3),getRandom(15,20));
        }
    }

    public void printTeam(){
        System.out.println("Команда "+name + ".Участники команды: ");
        for (int i = 0; i <  animals.length; i++) {
            animals[i].print();
            //System.out.println( animals[i].getType()+" "+ animals[i].getName());
        }
        System.out.println("********************************");
    }

    public void printResult(){
        System.out.println("\n********************************");
        System.out.println("Результаты: ");
        for (int i = 0; i <  animals.length; i++) {
            animals[i].printResult();
        }
    }

    int getRandom(int min, int max) {
        return ran.nextInt(max - min) + min;
    }
}
