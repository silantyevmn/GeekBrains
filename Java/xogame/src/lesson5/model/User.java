package lesson5.model;

/**
 * Java java1.lesson5.model
 *
 * @author Mikhail Silantev
 * @version date 09.08.2017.
 */
public class User implements Shootable{
    public static Point point;
    public volatile static boolean isShoot;

    @Override
    public Point getShoot() {
        isShoot=false;
        while (!isShoot);
        return point;
    }
}
