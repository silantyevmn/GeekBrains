package Test;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Test
 * Created by Михаил Силантьев on 23.10.2017.
 */
public class ChatWindow extends JFrame implements ActionListener {
    JButton b1;
    JButton b2;
    public static void main(String[] args) {
        new ChatWindow();
    }
    ChatWindow() {
        JButton b1 = new JButton("1");
        JButton b2 = new JButton("2");
        b1.addActionListener(this);
        b2.addActionListener(this);
        setSize(200,200);
        add(b1);
        add(b2);
        setVisible(true);

    }
        @Override
        public void actionPerformed (ActionEvent e){
            Object obj=e.getSource();
            if(obj==b1){
                //код
            }else if(obj==b2) {
                //код
            }
        }
    }

