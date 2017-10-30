/*package Java1.lesson5;*/

import java.util.Random;

/**
 * Java Java1.lesson5
 *
 * @author Mikhail Silantev
 * @version date 08.08.2017.
 */
 
/* Создать класс "Сотрудник" с полями: ФИО, должность, email, телефон, зарплата, возраст;
        * Конструктор класса должен заполнять эти поля при создании объекта;
        * Внутри класса «Сотрудник» написать метод, который выводит информацию об объекте в консоль;
        * Создать массив из 5 сотрудников
        Пример:
        Person[] persArray = new Person[5]; // Вначале объявляем массив объектов
        persArray[0] = new Person("Ivanov Ivan", "Engineer", "ivivan@mailbox.com", "892312312", 30000, 30); // потом для каждой ячейки массива задаем объект
        persArray[1] = new Person(...);
        ...
        persArray[4] = new Person(...);
        * С помощью цикла вывести информацию только о сотрудниках старше 40 лет;
*/

public class Lesson5 {
    static final int SIZE_PERSON = 5; //количество сотрудников
    static String[] fNameArrs = {"Петров", "Иванов", "Сидоров", "Судаков", "Путин"};
    static String[] ltNameArrs = {"Иван", "Владимир", "Сергей", "Михаил", "Василий"};
    static String[] professionArrs = {"sculptor", "programmer", "engineer", "president", "boss"};

    public static void main(String[] args) {

        Person[] personArrs = new Person[SIZE_PERSON];
        String name, profession, mail;
        int phone, salary, age;
        for (int i = 0; i < SIZE_PERSON; i++) {
            name = fNameArrs[setRandomValue(0, fNameArrs.length)] + " " + ltNameArrs[setRandomValue(0, ltNameArrs.length)];
            profession = professionArrs[setRandomValue(0, professionArrs.length)];
            mail = "" + setRandomValue(1, 9) + profession + "@mailbox.com";
            phone = setRandomValue(0, 999999999);
            salary = setRandomValue(35, 500);
            age = setRandomValue(18, 90);
            personArrs[i] = new Person(name, profession, mail, phone, salary, age);
        }

        for (Person per : personArrs) {
            per.show();
        }

        System.out.println("\nСотрудники старше 40 лет:");
        for (Person per : personArrs) {
            if (per.age > 40) per.show();
        }

    }

    public static int setRandomValue(int min, int max) {
        Random ran = new Random();
        return ran.nextInt(max - min) + min;
    }

}

class Person {

    String name;
    String profession;
    String mail;
    int phone;
    int salary;
    int age;

    public Person(String name, String profession, String mail, int phone, int salary, int age) {
        this.name = name;
        this.profession = profession;
        this.mail = mail;
        this.phone = phone;
        this.salary = salary;
        this.age = age;
    }

    void show() {
        System.out.printf("%s %s mail:%s phone:+79%d зарплата:%d000 возраст:%d\n", name, profession, mail, phone, salary, age);
    }
}
