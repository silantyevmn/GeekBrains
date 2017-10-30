package lesson5.model;

import lesson5.controler.GameControler;

/**
 * Java java1.lesson5.model
 *
 * @author Mikhail Silantev
 * @version date 09.08.2017.
 */
public class Field {
    public static final int SIZE_X=3;
    public static final int SIZE_Y=3;
    public static final int LENGTH_POINT=3;
    public enum Type{X,O,NONE};
    Type[][] cells;

    public Field() {
        cells=new Type[SIZE_X][SIZE_Y];
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j]= Type.NONE;
            }
        }
    }

    public void show(){
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
               switch (cells[i][j]){
                   case X:
                       System.out.print("X");
                       break;
                   case O:
                       System.out.print("O");
                       break;
                   case NONE:
                       System.out.print(".");
                       break;
               }
            }
            System.out.println(" ");
        }
        System.out.println();
    }

    public boolean doShoot(Point p,Type type){
        //todo необходима проверка на занятость точки
        if(cells[p.getX()][p.getY()]== Type.NONE) {
            cells[p.getX()][p.getY()] = type;
            //необходимо передать координаты полю
            GameControler.setPointComputer(p,type);
            return true;
        } else if(type== Type.X)
            System.out.println("Данная точка занята!");
        return false;
    }

    public boolean isCheckWin(Type type){
        //boolean isWin=false;
//        if(checkNotNone()) {
//            GameWindow.lmenu.setText("Ничья");
//            return true;
//        }
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length-2; j++) {
                if(cells[i][j]==type && cells[i][j+1]==type && cells[i][j+2]==type) {
                    return true;
                }
                if(cells[j][i]==type && cells[j+1][i]==type && cells[j+2][i]==type) {
                    return true;
                }
            }
        }
        for (int i = 0; i < cells.length-2; i++) {
            for (int j = 2; j < cells.length; j++) {
                if(cells[i][j]==type && cells[i+1][j-1]==type && cells[i+2][j-2]==type) {
                    return true;
                }
            }
        }
        for (int i = 0; i < cells.length-2; i++) {
            for (int j = 0; j < cells[i].length-2; j++) {
                if(cells[i][j]==type && cells[i+1][j+1]==type && cells[i+2][j+2]==type) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isCheckWin(){
        if(isCheckWin(Type.X)) return true;
        if(isCheckWin(Type.O)) return true;

        return false;
    }


    public boolean checkNotNone() {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if(cells[i][j]== Type.NONE)
                    return false;
            }
        }
        return true;
    }
}
