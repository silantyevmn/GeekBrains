package JavaCore.Lesson4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * 1. Создать окно для клиентской части чата: большое текстовое поле для отображения переписки в центре окна.
 * Однострочное текстовое поле для ввода сообщений и кнопка для отсылки сообщений на нижней панели.
 * Сообщение должно отсылаться либо по нажатию кнопки на форме, либо по нажатию кнопки Enter.
 * При «отсылке» сообщение перекидывается из нижнего поля в центральное.
 *
 * JavaCore.Lesson4
 * Created by Михаил Силантьев on 14.10.2017.
 */
public class MainWindow extends JFrame{
    JTextField jText;
    JTextArea jTextChat;

    public static void main(String[] args) {
        new MainWindow();
    }

    MainWindow(){
        setTitle("My Chat");
        setSize(400,400);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        jTextChat=new JTextArea();
        add(new JScrollPane(jTextChat));

        JPanel jp=new JPanel();
        jp.setLayout(new GridLayout(1,2));
        jText=new JTextField("введите текст");
        jp.add(jText);
        JButton jb1=new JButton("Отправить");
        jp.add(jb1);
        add(jp, BorderLayout.PAGE_END);
        jText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UpdateText();
            }
        });
        jb1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UpdateText();
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("До Свидания!");
            }
        });

        setVisible(true);
    }

    void UpdateText(){
        String text=jText.getText();
        if(text.hashCode()==0 || text==""){
            System.out.println("Вы не ввели текст");
            return;
        }
        jTextChat.append("\n"+text);
        jText.setText("");
    }
}
