package Java1.lesson4;

/**
 * Java Level 1. Lesson 4.
 *
 * @author Mikhail Silantev
 * @version dated 05.08.2017.
 */

public class StartGame {

    public static void main(String[] args) {
        FieldCell fieldCell = new FieldCell();
        fieldCell.init();
        fieldCell.show();
        fieldCell.startGame();
    }
}


