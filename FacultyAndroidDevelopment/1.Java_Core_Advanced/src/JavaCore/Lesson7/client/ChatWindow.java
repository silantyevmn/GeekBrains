package JavaCore.Lesson7.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

/**
 * JavaCore.JavaCore.Lesson7
 * Created by Михаил Силантьев on 24.10.2017.
 */
public class ChatWindow extends JFrame implements ActionListener {
    private final int SIZE_X = 400;
    private final int SIZE_Y = 400;
    private final int SERVER_PORT = 8190;
    private final String SERVER_ADRESS = "localhost";
    private JTextField jTextField;
    private JTextArea jTextArea;
    private JTextArea nikName;
    private JTextField loginText;
    private JPasswordField passwortText;
    private JButton jbLogin = new JButton("Логин");
    private JButton jbSend = new JButton("Отправить");
    private JPanel jpDown, jpUp,jpRigth;
    private DataInputStream in;
    private DataOutputStream out;
    private Socket socket=null;
    private boolean isAuthorized;

    public void setAuthorized(boolean authorized) { //скрываем панели для авторизованых и неавторизованых пользователей
        isAuthorized = authorized;
        jpUp.setVisible(!isAuthorized);
        jpDown.setVisible(isAuthorized);
    }

    ChatWindow() {
        setSize(SIZE_X, SIZE_Y);
        setTitle("ChatWindow");
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        jTextArea = new JTextArea();
        jTextArea.setEditable(false); //запрет на редактирование
        jTextArea.setLineWrap(true); //перенос текста
        JScrollPane jScrollPane = new JScrollPane(jTextArea);

        jpDown = new JPanel();
        jpDown.setLayout(new BorderLayout());
        //jpDown.setPreferredSize(new Dimension(200, 0));
        jTextField = new JTextField();
        jpDown.add(jTextField, BorderLayout.CENTER);
        jpDown.add(jbSend, BorderLayout.EAST);
        jbSend.addActionListener(this);
        jTextField.addActionListener(this);

        jpUp = new JPanel();
        jpUp.setLayout(new GridLayout(1, 3));
        loginText = new JTextField();
        loginText.setSize(new Dimension(100,30));
        passwortText = new JPasswordField();
        loginText.addActionListener(this);
        passwortText.addActionListener(this);
        jbLogin.addActionListener(this);
        jpUp.add(loginText);
        jpUp.add(passwortText);
        jpUp.add(jbLogin);

        jpRigth=new JPanel();
        jpRigth.setLayout(new BorderLayout());
        jpRigth.setPreferredSize(new Dimension(150,0));
        nikName = new JTextArea();
        nikName.setEditable(false); //запрет на редактирование
        nikName.setLineWrap(true); //перенос текста
        JScrollPane scrollPane = new JScrollPane(nikName);
        jpRigth.add(scrollPane);

        add(jpRigth,BorderLayout.LINE_END);
        add(jScrollPane, BorderLayout.CENTER);
        add(jpDown, BorderLayout.SOUTH);
        add(jpUp, BorderLayout.NORTH);
        setVisible(true);

        try {
            socket = new Socket(SERVER_ADRESS, SERVER_PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Нет соединения с сервером!");
            e.printStackTrace();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) { // авторизация пользователя
                        String msg = in.readUTF();
                        if (msg.startsWith("/authok")) {
                            setAuthorized(true);
                            break;
                        }
                        if (msg.startsWith("/online")) {
                            updatePersonOnline(msg);
                            continue;
                        }

                        jTextArea.append(msg + "\n");
                        jTextArea.setCaretPosition(jTextArea.getDocument().getLength());
                    }
                    while (true) {
                        String msg = in.readUTF();
                        if (msg.startsWith("/online")) {
                            updatePersonOnline(msg);
                            continue;
                        }
                        jTextArea.append(msg + "\n");
                        jTextArea.setCaretPosition(jTextArea.getDocument().getLength()); //перемещает курсор на самый последний символ чата
                    }
                } catch (IOException e) {
                    //e.printStackTrace();
                    setAuthorized(false);
                } finally {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                try{
                    out.writeUTF("/end");

                    socket.close();
                }catch(IOException e1){
                    e1.printStackTrace();
                    setAuthorized(false);
                }
            }
        });
        setAuthorized(false);
    }

    public void auth(){
        String autText="/auth ";
        if(loginText.getText().length()<2){
            autText="/login null";
        } else if(passwortText.getText().length()<2){
            autText="/password null";
        }
        try{
            out.writeUTF(autText + loginText.getText() + " " + passwortText.getText());
            loginText.setText("");
            passwortText.setText("");
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void sendMessage(){
        String msg = jTextField.getText();
        jTextField.setText("");
        try{
            out.writeUTF(msg);
            out.flush();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void updatePersonOnline(String msg){
        //todo Online
        nikName.setText("Онлайн:\n");
        String[] elements  = msg.split(" ");
        for (int i = 1; i < elements.length; i++) {
            nikName.append(elements[i]+"\n");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if (obj.equals(jTextField) || obj.equals(jbSend)) {
            sendMessage();
        } else if(obj.equals(loginText) || obj.equals(passwortText) || obj.equals(jbLogin)) auth();
//        else {
//            System.out.println(obj.toString() + " не назначен обработчик");
//        }
    }
}
