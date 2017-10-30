package Java1.lesson4;

import java.util.Random;

/**
 * Java Level 1. Lesson 4.
 *
 * @author Mikhail Silantev
 * @version dated 05.08.2017.
 */

public class Computer {
    char bot;
    int x;
    int y;
    Random ran = new Random();

    public Computer(char bot) {
        this.bot = (bot == 'X') ? 'O' : 'X';
    }

    void setPoint(int size) {
        x = ran.nextInt(size);
        y = ran.nextInt(size);
    }

    void intelectComputer(int lengthVictory, char playerBot) {
        checkPointField(lengthVictory, playerBot);
    }

    void checkPointField(int lengthVictory, char bot) {
        for (int i = 0; i < FieldCell.maps.length; i++) {
            for (int j = 0; j < FieldCell.maps[i].length; j++) {
                if (FieldCell.maps[i][j] == bot) {
                    // проверка координат компьютера, если не заканчиваем игру, то блокирование игрока
                    if (!check(i, j, this.bot, lengthVictory)) {
                        check(i, j, bot, lengthVictory);
                    }
                }
            }
        }
    }


    private boolean check(int i, int j, char bot, int lengthVictory) {
        int numHorizontale = 0, numVerticale = 0, numDig1 = 0, numDig2 = 0;
        for (int k = 0; k < lengthVictory; k++) {
            //проверка по горизонтали
            if (lengthVictory + j <= FieldCell.maps.length) {
                numHorizontale += (FieldCell.maps[i][j + k] == bot) ? 1 : 0;
            }
            //проверка по вертикали
            if (lengthVictory + i <= FieldCell.maps.length) {
                numVerticale += (FieldCell.maps[i + k][j] == bot) ? 1 : 0;
            }
            //проверка по диагонали 00 11 22
            if (lengthVictory + i <= FieldCell.maps.length && lengthVictory + j <= FieldCell.maps.length) {
                numDig1 += (FieldCell.maps[i + k][j + k] == bot) ? 1 : 0;
            }
            //проверка по диагонали 20 11 02
            if (i >= lengthVictory - 1 && lengthVictory + j <= FieldCell.maps.length) {
                numDig2 += (FieldCell.maps[i - k][j + k] == bot) ? 1 : 0;
            }

            // интелект компьютера по горизонтали
            int mapsLength = FieldCell.maps.length - 1;
            if (numHorizontale == lengthVictory) {
                int tempX = i;
                int tempY = j + k;
                if (tempY + 1 <= mapsLength && FieldCell.maps[tempX][tempY + 1] == '.') {
                    x = i;
                    y = tempY + 1;
                } else if (tempY - 1 >= 0 && FieldCell.maps[tempX][tempY - 1] == '.') {
                    x = i;
                    y = tempY - 1;
                } else if (tempY + lengthVictory <= mapsLength && FieldCell.maps[tempX][tempY + lengthVictory] == '.') {
                    x = i;
                    y = tempY + lengthVictory;
                } else if (tempY - lengthVictory >= 0 && FieldCell.maps[tempX][tempY - lengthVictory] == '.') {
                    x = i;
                    y = tempY - lengthVictory;
                }
            }
            // интелект компьютера по вертикали
            if (numVerticale == lengthVictory) {
                int tempX = i + k;
                int tempY = j;
                if (tempX + 1 <= mapsLength && FieldCell.maps[tempX + 1][tempY] == '.') {
                    x = tempX + 1;
                    y = tempY;
                } else if (tempX - 1 >= 0 && FieldCell.maps[tempX - 1][tempY] == '.') {
                    x = tempX - 1;
                    y = tempY;
                } else if (tempX + lengthVictory <= mapsLength && FieldCell.maps[tempX + lengthVictory][tempY] == '.') {
                    x = tempX + lengthVictory;
                    y = tempY;
                } else if (tempX - lengthVictory >= 0 && FieldCell.maps[tempX - lengthVictory][tempY] == '.') {
                    x = tempX - lengthVictory;
                    y = tempY;
                }
            }
            // интелект компьютера по диагонали
            if (numDig1 == lengthVictory) {
                int tempX = i + k;
                int tempY = j + k;
                if (tempX + 1 <= mapsLength && tempY + 1 <= mapsLength && FieldCell.maps[tempX + 1][tempY + 1] == '.') {
                    x = tempX + 1;
                    y = tempY + 1;
                } else if (tempX - 1 >= 0 && tempY - 1 >= 0 && FieldCell.maps[tempX - 1][tempY - 1] == '.') {
                    x = tempX - 1;
                    y = tempY - 1;
                } else if (tempX + lengthVictory <= mapsLength && tempY + lengthVictory <= mapsLength && FieldCell.maps[tempX + lengthVictory][tempY + lengthVictory] == '.') {
                    x = tempX + lengthVictory;
                    y = tempY + lengthVictory;
                } else if (tempX - lengthVictory >= 0 && tempY - lengthVictory >= 0 && FieldCell.maps[tempX - lengthVictory][tempY - lengthVictory] == '.') {
                    x = tempX - lengthVictory;
                    y = tempY - lengthVictory;
                }
            }
            // интелект компьютера по диагонали
            if (numDig2 == lengthVictory) {
                int tempX = i - k;
                int tempY = j + k;
                if (tempX - 1 >= 0 && tempY + 1 <= mapsLength && FieldCell.maps[tempX - 1][tempY + 1] == '.') {
                    x = tempX - 1;
                    y = tempY + 1;
                } else if (tempX + 1 <= mapsLength && tempY - 1 >= 0 && FieldCell.maps[tempX + 1][tempY - 1] == '.') {
                    x = tempX + 1;
                    y = tempY - 1;
                } else if (tempX - lengthVictory >= 0 && tempY + lengthVictory <= mapsLength && FieldCell.maps[tempX - lengthVictory][tempY + lengthVictory] == '.') {
                    x = tempX - lengthVictory;
                    y = tempY + lengthVictory;
                } else if (tempX + lengthVictory <= mapsLength && tempY - lengthVictory >= 0 && FieldCell.maps[tempX + lengthVictory][tempY - lengthVictory] == '.') {
                    x = tempX + lengthVictory;
                    y = tempY - lengthVictory;
                }
            }
            if (numHorizontale == lengthVictory || numVerticale == lengthVictory ||
                    numDig1 == lengthVictory || numDig2 == lengthVictory) return true;
        }
        return false;
    }

}
