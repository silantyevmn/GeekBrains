package JavaCore.Lesson1.obstacles;

import JavaCore.Lesson1.logic.*;

/**
 * JavaCore.Lesson1
 * Created by Михаил Силантьев on 04.10.2017.
 */
public class Wall extends Obstacle {
    int height;
    public Wall(int height){
        this.height = height;
    }
    @Override
    public void doIt(iChallenagable c){
        c.jump(this.height);
    }
}
