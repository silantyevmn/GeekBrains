package JavaCore.Lesson1.logic;

import JavaCore.Lesson1.obstacles.Obstacle;

/**
 * JavaCore.Lesson1
 * Created by Михаил Силантьев on 04.10.2017.
 */
public class Cource {
    Obstacle[] obstacles;

    public Cource(Obstacle[] obstacles) {
        this.obstacles = obstacles;
    }

    public void start(Team t) {
        for(int i = 0; i < t.animals.length; i++){
            for(int j = 0; j < obstacles.length; j++){
                obstacles[j].doIt(t.animals[i]);
                if(!t.animals[i].isOnDistance()) break;
            }
        }
    }
}
