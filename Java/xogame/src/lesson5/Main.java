package lesson5;


import lesson5.model.Computer;
import lesson5.model.Field;
import lesson5.model.User;
import lesson5.view.GameWindow;

/**
 * Java java1.lesson5
 *
 * @author Mikhail Silantev
 * @version date 09.08.2017.
 */
public class Main {

    public static void main(String[] args) {
        Field field=new Field();
        User user=new User();
        Computer comp=new Computer();
        GameWindow gameWindow=new GameWindow();
        gameWindow.init();
        //field.show();
        while (!field.isCheckWin()){
            int winComp=0,winUser=0; // 0 компьютер 1- блокировка игрока
            if(!comp.intelect(field, Field.Type.O)){
                if(!comp.intelect(field, Field.Type.X)){
                    while (!field.doShoot(comp.getShoot(),Field.Type.O));
                }
            } //else field.doShoot(comp.getPoint(),Field.Type.O);


            //field.show();
            if(field.isCheckWin(Field.Type.O)) {
                System.out.println("Компьютер выиграл.");
                GameWindow.lmenu.setText("Компьютер выиграл.");
                break;
            }
            if(field.checkNotNone()) {
                GameWindow.lmenu.setText("Ничья");
                break;
            }
//            while (!field.doShoot(comp.getShoot(),Field.Type.O));
//            field.show();
//            if(field.isCheckWin(Field.Type.O)) break;

            while (!field.doShoot(user.getShoot(),Field.Type.X));
            if(field.isCheckWin(Field.Type.X)) {
                System.out.println("Поздравляем!");
                GameWindow.lmenu.setText("Поздравляем!");
                break;
            }
            if(field.checkNotNone()) {
                GameWindow.lmenu.setText("Ничья");
                break;
            }
            //field.show();
        }
        //field.show();
        //GameControler.gameOver();
    }

}
