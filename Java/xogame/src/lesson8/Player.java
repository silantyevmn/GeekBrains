package lesson8;

/**
 * Java Java1.lesson8
 *
 * @author Mikhail Silantev
 * @version date 20.08.2017.
 */
public class Player {
    private final char DOT;

    Player(char ch) { DOT = ch; }

    void turn(int x, int y, Field field, Computer computer) {
        if (field.isCellEmpty(x, y)) {
            if (!field.isGameOver()) field.setDot(x, y, DOT);
            if (!field.isGameOver()) computer.turn(field);
        }
    }
}
