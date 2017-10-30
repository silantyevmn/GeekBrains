package Java1.lesson8;

import java.util.Random;

/**
 * Java Java1.lesson8
 *
 * @author Mikhail Silantev
 * @version date 20.08.2017.
 */
public class Computer {
    Random random = new Random();
    private final char DOT;

    Computer(char ch) { DOT = ch; }

    void turn(Field field) {
        if(!isWin(field,'o')){
            if(!isWin(field,'x')){
                int x, y;
                do {
                    x = random.nextInt(field.getSize());
                    y = random.nextInt(field.getSize());
                } while (!field.isCellEmpty(x, y));
                field.setDot(x, y, DOT);
            }
        }
    }

    boolean isWin(Field field,char c){
        for (int i = 0; i < field.getSize(); i++) {
            for (int j = 0; j < field.getSize(); j++) {
                if (field.field[i][j] == '.') {
                    field.field[i][j] = c;
                    if (field.isWin(c)) {
                        field.field[i][j] = '.';
                        field.setDot(i, j, 'o');
                        return true;
                    }
                    field.field[i][j] = '.';
                }
            }
        }
        return false;
    }
}
