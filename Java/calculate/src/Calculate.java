import javafx.scene.layout.GridPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Java 1 Lesson 8
 *
 * @author Mikhail Silantev
 * @version date 24.08.2017.
 */
public class Calculate extends JFrame {
    JTextField textField;
    JLabel textLabel;
    public static void main(String[] args) {
        new Calculate();
    }

    Calculate(){
        setTitle("Калькулятор");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 400);
        setLocationRelativeTo(null); // to the center
        //setResizable(false);

        textField=new JTextField(25);
        textLabel=new JLabel();
        JButton buttonCls=new JButton("C");
        JPanel panelUp=new JPanel();
        panelUp.setLayout(new GridLayout(2,1));
        panelUp.add(textLabel);
        //panelUp.add(textField);
        panelUp.add(buttonCls);

        JPanel winpanel=new JPanel();
        winpanel.setLayout(new BorderLayout());
        winpanel.add(panelUp,BorderLayout.NORTH);
        //winpanel.add("North",textField);

        String arrNameButtons[]={"1","2","3","4","5","6","7","8","9","0",".","=","/","*","-","+"};
        JButton[] jButtons=new JButton[arrNameButtons.length];

        JPanel p0=new JPanel();
        p0.setLayout(new GridLayout(1,1));

        JPanel p1=new JPanel();
        p1.setLayout(new GridLayout(4,3));

        JPanel p2=new JPanel();
        p2.setLayout(new GridLayout(4,1));

        for (int i = 0; i < jButtons.length; i++){
            jButtons[i] = new JButton(arrNameButtons[i]);
            p1.add(jButtons[i]);
            if(i>11) p2.add(jButtons[i]);
            else
                p1.add(jButtons[i]);
        }
        winpanel.add(p1,BorderLayout.CENTER);
        winpanel.add(p2, BorderLayout.LINE_END);

        add(winpanel);
        pack();
        setVisible(true);
        CalculateEngine calculateEngine=new CalculateEngine(this);
        buttonCls.addActionListener(calculateEngine);
        for (int i = 0; i < jButtons.length; i++) {
            jButtons[i].addActionListener(calculateEngine);
        }
    }
}
