package JavaCore.Lesson3;

import java.util.*;

/**
/*1. Создать массив с набором слов (10-20 слов, должны встречаться повторяющиеся).
Найти и вывести список уникальных слов, из которых состоит массив (дубликаты не считаем).
Посчитать сколько раз встречается каждое слово.

2. Написать простой класс ТелефонныйСправочник, который хранит в себе список фамилий и телефонных номеров.
 В этот телефонный справочник с помощью метода add() можно добавлять записи. С помощью метода get() искать номер телефона по фамилии.
 Следует учесть, что под одной фамилией может быть несколько телефонов (в случае однофамильцев),
  тогда при запросе такой фамилии должны выводиться все телефоны.
* JavaCore.Lesson3
 * Created by Михаил Силантьев on 11.10.2017.
 */

public class Main {
    static final int SIZE=10;
    public static void main(String[] args) {
        //1
        String[] words={"black","while","while","red","blu","red","green","blu","black","black","people","apple","yelow"};
        Map<String, Integer> map = new TreeMap<>();
        Set<String> hashSet= new TreeSet<>();
        for (String word : words) {
            map.put(word, map.getOrDefault(word, 0) + 1);
            hashSet.add(word);
        }
        System.out.println(hashSet);
        System.out.println(map);

        //2
        PhoneBook pb=new PhoneBook();
        pb.add("Иванов","89099252233");
        pb.add("Иванов","84955990011");
        pb.add("Петров","84992077752");
        pb.add("Петров","02");

        System.out.println("Иванов: "+pb.get("Иванов"));
        System.out.println(pb.print());
    }

}
