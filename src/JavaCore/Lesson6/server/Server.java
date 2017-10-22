package JavaCore.Lesson6.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * JavaCore.Lesson6
 * Created by Михаил Силантьев on 20.10.2017.
 */
public class Server {
    public static void main(String[] args) {
        ServerSocket server = null;
        Socket socket = null;
        Thread t1 = null;
        try {
            server = new ServerSocket(8189);
            System.out.println("Сервер запущен, ждем клиентов");
            socket = server.accept(); //сервер ждет коннектов
            System.out.println("Клиент подключился!");
            Scanner scanner = new Scanner(System.in);
            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        String text = scanner.nextLine();
                        if (text.equals("")) continue;
                        out.println("сервер: " + text);
                        out.flush();
                    }
                }
            });
            t1.start();
            while (true) {
                String str = in.nextLine();
                System.out.println("клиент: " + str);
                out.println("клиент: " + str); //cкладывается в буфер
                out.flush();
            }
        } catch (IOException e) {
            System.out.println("Ошибка инициализации сервера");
            //e.printStackTrace();
        } finally {
            try {
                server.close();
                t1.interrupt();
                t1 = null;
                System.out.println("Сервер закрылся");
                System.exit(-1);
            } catch (Exception e) {
                System.out.println("Сервер не смог закрыться");
                //e.printStackTrace();
            }
        }

    }
}
