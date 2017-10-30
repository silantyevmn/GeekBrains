package lesson5.model;

/**
 * Java java1.lesson5.model
 *
 * @author Mikhail Silantev
 * @version date 09.08.2017.
 */
public class Computer implements Shootable{
    Point point;

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    @Override
    public Point getShoot() {
        this.point=Point.getRandomPoint(Field.SIZE_X,Field.SIZE_Y);
        return point;
    }

    public boolean intelect(Field field, Field.Type type){
        for (int i = 0; i < Field.SIZE_X; i++) {
            for (int j = 0; j < Field.SIZE_Y; j++) {
                if (field.cells[i][j] == Field.Type.NONE) {
                    field.cells[i][j] = type;
                    if (field.isCheckWin(type)) {
                        field.cells[i][j] = Field.Type.NONE;
                        field.doShoot(new Point(i, j), Field.Type.O);
                        return true;
                    } else field.cells[i][j] = Field.Type.NONE;

                }
            }
        }
        return false;
    }
}
