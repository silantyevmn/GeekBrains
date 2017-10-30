package lesson8;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Java Java1.lesson8
 *
 * @author Mikhail Silantev
 * @version date 20.08.2017.
 */
public class XOGame extends JFrame {
    final String TITLE_OF_PROGRAM = "Крестики-нолики";
    final int WINDOW_SIZE = 330;
    final int WINDOW_DX = 7;
    final int WINDOW_DY = 55;
    final int FIELD_SIZE = 3;
    final int CELL_SIZE = WINDOW_SIZE / FIELD_SIZE;
    final String BTN_INIT = "Новая игра";
    final String BTN_EXIT = "Выход";

    Canvas canvas = new Canvas();
    Field field = new Field(FIELD_SIZE, CELL_SIZE);
    Player player = new Player(field.getHumanDot());
    Computer comp = new Computer(field.getAIDot());

    public static void main(String[] args) {
        new XOGame();
    }

    XOGame() {
        setTitle(TITLE_OF_PROGRAM);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WINDOW_SIZE + WINDOW_DX, WINDOW_SIZE + WINDOW_DY);
        setLocationRelativeTo(null); // to the center
        setResizable(false);

        canvas.setBackground(Color.white);
        comp.turn(field);
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                player.turn(e.getX() / CELL_SIZE, e.getY() / CELL_SIZE, field, comp);
                canvas.repaint();
                if (field.isGameOver())
                    JOptionPane.showMessageDialog(
                            XOGame.this, field.getGameOverMsg());
            }
        });
        JButton init = new JButton(BTN_INIT);
        init.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                field.init();
                comp.turn(field);
                canvas.repaint();
            }
        });
        JButton exit = new JButton(BTN_EXIT);
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JPanel bp = new JPanel();
        bp.setLayout(new GridLayout()); // for button panel
        bp.add(init);
        bp.add(exit);

        setLayout(new BorderLayout()); // for main window
        add(bp, BorderLayout.SOUTH);
        add(canvas, BorderLayout.CENTER);
        setVisible(true);
    }


    class Canvas extends JPanel { // for painting
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            field.paint(g);
        }
    }
}

