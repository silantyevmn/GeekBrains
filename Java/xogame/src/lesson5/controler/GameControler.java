package lesson5.controler;


import lesson5.model.Field;
import lesson5.model.Point;
import lesson5.model.User;
import lesson5.view.GameWindow;

/**
 * Java java1.lesson5.controler
 *
 * @author Mikhail Silantev
 * @version date 09.08.2017.
 */
public class GameControler {

    public static void doShoot(Point point, Field.Type type){
        User.point=point;
        User.isShoot=true;
    }

    public static void setPointComputer(Point p, Field.Type t){
        GameWindow.jButtons[p.getX()][p.getY()].setText((t==Field.Type.NONE)? "":t.toString());
    }

    public static void gameOver() {
        GameWindow.lmenu.setText("Конец игры!");
    }
}
