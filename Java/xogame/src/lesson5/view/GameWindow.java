package lesson5.view;


import lesson5.controler.GameControler;
import lesson5.model.*;
import lesson5.model.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Java java1.lesson5.view
 *
 * @author Mikhail Silantev
 * @version date 09.08.2017.
 */
public class GameWindow extends JFrame {
    JPanel jPanel=new JPanel();
    JPanel menu=new JPanel();
    public static Label lmenu;
    public static JButton[][] jButtons=new JButton[Field.SIZE_X][Field.SIZE_Y];

    public void init(){
        setSize(600,400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("XO Game");
        lmenu=new Label("Игра крестики нолики.");
        menu.add(lmenu);
        add(menu,BorderLayout.NORTH);
        jPanel.setLayout(new GridLayout(Field.SIZE_X,Field.SIZE_Y));
        for (int i = 0; i < jButtons.length; i++) {
            for (int j = 0; j < jButtons.length; j++) {
                JButton tempButton=new JButton("");
                jButtons[i][j]=tempButton;
                jPanel.add(tempButton);
                int finalI = i;
                int finalJ = j;

                tempButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //System.out.printf("x=%d y=%d\n", finalI, finalJ);
                        //tempButton.setText("X");
                        GameControler.doShoot(new Point(finalI,finalJ), Field.Type.X);

                    }
                });
            }
        }
        add(jPanel);
        setVisible(true);
    }
}
