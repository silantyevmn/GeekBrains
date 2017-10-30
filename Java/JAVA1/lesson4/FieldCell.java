package Java1.lesson4;

/**
 * Java Level 1. Lesson 4.
 *
 * @author Mikhail Silantev
 * @version dated 05.08.2017.
 */

public class FieldCell {
    static final char EMPTY_DOT = '.'; //знак пустого поля
    public static char[][] maps = new char[3][3]; // массив поля, по умолчанию 3*3
    int size = 3; // размер поля, по умолчанию 3;
    int lengthVictory = 3; // размер проверяемого блока на выигрыш, по умолчанию 3,если поле больше 3, устанавливается под размер поля xxx ooo
    int counter = 0; // счетчик ходов Игрока;
    String textGameOver; //текст окончания игры;
    Player player; // класс игрока
    Computer computer; // класс компьютера

    public void startGame() {
        while (!isGameOver()) {
            setCellsPlayer(); //запуск игрока
            setCellsComputer(); //запуск компьютера.
        }
        System.out.println("Вот и завершился матч! ");
        System.out.println(textGameOver);
    }

    //создать поле и заполнить его
    void init() {
        player = new Player();
        computer = new Computer(player.bot);
//        size=4;
//        maps=new char[size][size];
//        lengthVictory=4;
        System.out.println("Введите размер поля 3,4 или 5 :");
        size = player.inputScannerInt(size, 5);
        maps = new char[size][size];
        System.out.println("Проверка блока автоматически установленна в значение :" + size);
        lengthVictory = size;
        int n = 0;
        for (int i = 0; i < maps.length; i++) {
            for (int j = 0; j < maps[i].length; j++) {
                maps[i][j] = EMPTY_DOT;
            }
        }
    }

    // вывести на экран поле
    void show() {
        System.out.println();
        for (int i = 0; i < maps.length; i++) {
            for (int j = 0; j < maps[i].length; j++) {
                if (j == 0) {
                    System.out.print(i + "| " + maps[i][j] + " ");
                    continue;
                }
                if (j == maps[i].length - 1) {
                    System.out.print(maps[i][j] + " | ");
                    continue;
                }
                System.out.print(maps[i][j] + " ");
            }
            System.out.println();
        }
        System.out.print("   ");
        for (int i = 0; i < maps.length; i++) {
            System.out.print(i + " ");
        }
        System.out.printf("\nПоле:%d*%d Игрок:%s Компьютер:%s Проверка:%d Ходов:%d\n", size, size, player.bot, computer.bot, lengthVictory, counter);
    }

    // ход игрока
    void setCellsPlayer() {
        while (!isGameOver()) {
            player.setPlayerPoint(0, size - 1);
            counter++;
            if (maps[player.x][player.y] == '.') {
                maps[player.x][player.y] = player.bot;
                break;
            } else {
                System.out.println("Данные координаты заняты, попробуйте еше раз.");
            }
        }
        // show();
    }

    //ход компьютера
    private void setCellsComputer() {
        while (!isGameOver()) {
            computer.setPoint(size); // запрашиваем рандомные точки
            computer.intelectComputer(lengthVictory - 1, player.bot);
            if (maps[computer.x][computer.y] == '.') {
                maps[computer.x][computer.y] = computer.bot; //устанавливаем в пустую точку символ
                break;
            }
        }
        show();
    }

    private boolean isGameOver() {
        //return  false;
        //если игрок выиграл
        if (isCheckPointFieldGameOver(player.bot)) {
            show();
            textGameOver = "Поздравляем! " + player.name + " с победой! \nЗатрачено " + counter + " хода(ов)";
            return true;
        }
        // если компьютер выиграл
        if (isCheckPointFieldGameOver(computer.bot)) {
            textGameOver = "Ты проиграл! Попрубуй свои силы в другой раз.....";
            return true;
        }
        //если нет ходов
        if (isNotCheckPoint()) {
            textGameOver = "Ничья...о_О";
            return true;
        }
        return false;
    }

    boolean isNotCheckPoint() {
        for (int i = 0; i < maps.length; i++) {
            for (int j = 0; j < maps[i].length; j++) {
                if (maps[i][j] == '.') return false;
            }
        }
        return true;
    }

    boolean isCheckPointFieldGameOver(char simbol) {
        //если по горизонтали
        for (int i = 0; i < maps.length; i++) {
            for (int j = 0; j < maps[i].length; j++) {
                if (maps[i][j] == simbol) {
                    if (check(i, j, simbol)) return true;
                }
            }
        }
        return false;
    }

    private boolean check(int i, int j, char simbol) {
        int numHorizontale = 0, numVerticale = 0, numDig1 = 0, numDig2 = 0;
        for (int k = 0; k < lengthVictory; k++) {
            //проверка по горизонтали
            if (lengthVictory + j <= maps.length) {
                numHorizontale += (maps[i][j + k] == simbol) ? 1 : 0;
            }
            //проверка по вертикали
            if (lengthVictory + i <= maps.length) {
                numVerticale += (maps[i + k][j] == simbol) ? 1 : 0;
            }
            //проверка по диагонали 00 11 22
            if (lengthVictory + i <= maps.length && lengthVictory + j <= maps.length) {
                numDig1 += (maps[i + k][j + k] == simbol) ? 1 : 0;
            }
            //проверка по диагонали 20 11 02
            if (i >= lengthVictory - 1 && lengthVictory + j <= maps.length) {
                numDig2 += (maps[i - k][j + k] == simbol) ? 1 : 0;
            }
            if (numHorizontale == lengthVictory || numVerticale == lengthVictory ||
                    numDig1 == lengthVictory || numDig2 == lengthVictory) return true;
        }
        return false;
    }
}