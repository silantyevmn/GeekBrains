package JavaCore.Lesson7.server;

import java.io.*;
import java.net.Socket;

/**
 * JavaCore.Lesson7.server
 * Created by Михаил Силантьев on 24.10.2017.
 */
public class ClientHandler {
    private Server server;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String name;
    public String getName(){
        return name;
    }
    public ClientHandler(Socket socket,Server server){
        try {
            this.socket=socket;
            this.server=server;
            name = "noname";
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

        } catch (IOException e){
            e.printStackTrace();
        }
        new Thread(() -> {
            try{
                //Авторизация
                while(true){
                    String msg = in.readUTF();
                    if(msg.startsWith("/login")){
                        sendMessage("Логин не может быть пустым или менее 2-х символов!");
                    } else if(msg.startsWith("/password")){
                        sendMessage("Пароль не может быть пустым или менее 2-х символов!");
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
                                break;
                            }else sendMessage("Учетная запись уже используется");
                        }else sendMessage("Не верные логин/пароль");
                    }else sendMessage("Для начала надо авторизоваться!");
                } //пока не прервется цикл авторизации, не начнется цикл приема сообщений
                while(true){
                    String msg = in.readUTF();
                    System.out.println(name+" "+msg);
                    if(msg.equalsIgnoreCase("/end")) {
                        server.broadcast(this.name + " покидает чат");
                        break;
                    }
                    //todo /w nick3
                    if(msg.startsWith("/w")){
                        String[] elements  = msg.split(" ");
                        server.privateSend(elements[1],elements[2]);
                        sendMessage(this.name+" to "+elements[1]+": "+elements[2]);
                        continue;
                    }
                    server.broadcast(this.name + " " + msg);
                }
            }catch(IOException e){
                e.printStackTrace();
            }finally{

                server.unSubscribeMe(this);
                server.getCliensOnline("/online ");
                try{
                    socket.close();
                }catch(IOException e){
                    //e.printStackTrace();
                }
            }
        }).start();
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
