package JavaCore.Lesson1.obstacles;

import JavaCore.Lesson1.logic.*;

/**
 * JavaCore.Lesson1
 * Created by Михаил Силантьев on 04.10.2017.
 */
public class Pool extends Obstacle {
    int length;
    public Pool(int length){
        this.length = length;
    }
    @Override
    public void doIt(iChallenagable c){
        c.swim(this.length);
    }
}
