package JavaCore.Lesson8.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 * JavaCore.Lesson8.server
 * Created by Михаил Силантьев on 30.10.2017.
 */
public class ClientHandler {
    private Server server;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String name;
    private int timeout;

    public String getName(){
        return name;
    }
    public ClientHandler(Socket socket,Server server){
        try {
            this.socket = socket;
            this.server = server;
            this.timeout = server.getTimeout();
            name = "noname";
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e){
            e.printStackTrace();
        }
        new Thread(() -> {
            try{
                //Авторизация
                while(true) {
                    socket.setSoTimeout(timeout);
                    String msg = in.readUTF();
                    if(authorizationClient(msg)) {
                        socket.setSoTimeout(0);
                        break; //если авторизация прошла успешно, то выход из цикла
                    }
                }
                //Чтение сообщений от клиента
                while(true){
                    String msg = in.readUTF();
                    System.out.println(name+" "+msg);
                    if(readMessage(msg)){
                        server.broadcast(this.name + " покидает чат");
                        break;
                    }
                }
            }catch (SocketTimeoutException e) {
                if (name.equals("noname")) {
                    sendMessage("/timeoff");
                }
            }catch(IOException e){
                //e.printStackTrace();
            }finally{
                server.unSubscribeMe(this);
                server.getCliensOnline("/online ");
                System.out.println(name+" отключился");
                try{
                    socket.close();
                }catch(IOException e){
                    //e.printStackTrace();
                }
            }
        }).start();
    }

    synchronized boolean authorizationClient(String msg){
            if(msg.startsWith("/login")){
                sendMessage("Логин не может быть пустым или менее 2-х символов!");
            } else if(msg.startsWith("/password")) {
                sendMessage("Пароль не может быть пустым или менее 2-х символов!");
//            } else if(msg.startsWith("/timeout")){
//                int clientTimeout=Integer.parseInt(msg.split("")[1]);
//                if(clientTimeout==timeout){
//                    sendMessage("/timeoff");
//                }
            } else if(msg.startsWith("/auth")){
                String[] elements  = msg.split(" ");
                String nick = server.getAuthService().getNickByLoginPass(elements[1], elements[2]);
                //System.out.println(nick);
                if(nick != null){ // если пользователь указал правильные логин/пароль
                    if(!server.isNickBusy(nick)){
                        this.name = nick;
                        sendMessage("/authok " + nick);
                        server.broadcast(this.name + " зашел в чат");
                        System.out.println(this.name + " зашел в чат");
                        server.getCliensOnline("/online ");//выводим онлайн клиентов у всех
                        return true;
                    }else sendMessage("Учетная запись уже используется");
                }else sendMessage("Не верные логин/пароль");
            }else sendMessage("Для начала надо авторизоваться!");
         //пока не прервется цикл авторизации, не начнется цикл приема сообщений
        return false;
    }

    synchronized boolean readMessage(String msg){
        if(msg.startsWith("/")){
            //разбиение служебных сообщений
            String[] elements  = msg.split(" ");
            if(msg.equalsIgnoreCase("/end")) {
                return true;
            }else if(elements[0].equals("/w")){
                String nameTo = msg.split(" ")[1];
                String message = msg.substring(4 + nameTo.length()); //4 = / + w + 2 пробела (до ника и после)
                server.privateSend(nameTo,message,this);
                return false;
            } else{
                sendMessage("такой команды нет!");
            }
        } else
            server.broadcast(this.name + " " + msg);
        return false;
    }

    public void sendMessage(String msg){
        try{
            out.writeUTF(msg);
            out.flush();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
