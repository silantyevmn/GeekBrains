package JavaCore.Lesson3;

import com.sun.javafx.collections.MappingChange;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * 2. Написать простой класс ТелефонныйСправочник, который хранит в себе список фамилий и телефонных номеров.
 В этот телефонный справочник с помощью метода add() можно добавлять записи. С помощью метода get() искать номер телефона по фамилии.
 Следует учесть, что под одной фамилией может быть несколько телефонов (в случае однофамильцев),
 тогда при запросе такой фамилии должны выводиться все телефоны.

 * JavaCore.Lesson3
 * Created by Михаил Силантьев on 11.10.2017.
 */
public class Person {
    private HashMap<String,HashSet<String>> map;

    public Person() {
        map=new HashMap<>();
    }

    public void add(String name, String phone){
        HashSet<String> phones;
        if (map.containsKey(name)) {
            phones = map.get(name);
        } else {
            phones = new HashSet<>();
        }
        phones.add(phone);
        map.put(name,phones);
    }

    public HashSet<String> get (String name){
        return map.get(name);
    }
}
