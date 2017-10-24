package Lesson7.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * JavaCore.Lesson7
 * Created by Михаил Силантьев on 24.10.2017.
 */
public class ChatWindow extends JFrame {
    private final int SIZE_X = 400;
    private final int SIZE_Y = 400;
    private final int SERVER_PORT = 8189;
    private final String SERVER_ADRESS = "localhost";
    private JTextField jTextField;
    private JTextArea jTextArea;
    private Scanner in;
    private PrintWriter out;
    private Socket socket;

    ChatWindow() {
        try {
            socket = new Socket(SERVER_ADRESS, SERVER_PORT);
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Нет соединения с сервером!");
            //e.printStackTrace();
        }
        setSize(SIZE_X, SIZE_Y);
        setTitle("ChatWindow");
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        jTextArea = new JTextArea();
        jTextArea.setEditable(false); //запрет на редактирование
        jTextArea.setLineWrap(true); //перенос текста
        JScrollPane jScrollPane = new JScrollPane(jTextArea);

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());
        jPanel.setPreferredSize(new Dimension(200, 50));
        jTextField = new JTextField();
        JButton jButton = new JButton("send");
        jPanel.add(jTextField, BorderLayout.CENTER);
        jPanel.add(jButton, BorderLayout.EAST);

        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
        jTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        if (in.hasNext()) {
                            String text = in.nextLine();
                            jTextArea.append(text);
                            jTextArea.append("\n");
                        }
                    } catch (Exception e) {
                        System.out.println("Ошибка! программа будет закрыта!");
                        System.exit(-1);
                        //e.printStackTrace();
                    }
                }
            }
        }).start();

        add(jScrollPane, BorderLayout.CENTER);
        add(jPanel, BorderLayout.SOUTH);
        setVisible(true);


    }

    private void sendMessage() {
        String text = jTextField.getText();
        out.println(text);
        out.flush();
        jTextField.setText("");
    }

}
