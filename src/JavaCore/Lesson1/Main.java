package JavaCore.Lesson1;

import JavaCore.Lesson1.logic.*;
import JavaCore.Lesson1.obstacles.*;


/**
 * JavaCore.Lesson1
 * Created by Михаил Силантьев on 04.10.2017.
 */
public class Main {

    public static void main(String[] args) {
        Team t=new Team("Команда",8);
        t.printTeam();
        Obstacle[] obstacles = {new Cross(900), new Wall(1), new Pool(18)};
        Cource c=new Cource(obstacles);
        c.start(t);
        t.printResult();
    }
}

/*Разобраться с имеющимся кодом;
Добавить класс Team, который будет содержать: название команды, массив из 4-х участников (т.е. в конструкторе можно сразу всех участников указывать),
метод для вывода информации о членах команды прошедших дистанцию, метод вывода информации обо всех членах команды.
Добавить класс Course (полоса препятствий), в котором будут находиться: массив препятствий, метод который будет просить команду пройти всю полосу;
Разбить код на packages*/