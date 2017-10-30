package JavaCore.Lesson7.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/**
 * JavaCore.JavaCore.Lesson7
 * Created by Михаил Силантьев on 24.10.2017.
 */
public class Server {
   private final int PORT=8190;
   Vector<ClientHandler> clients;
    ServerSocket serverSocket=null;
    Socket socket=null;
    AuthService authService;

    public AuthService getAuthService(){
        return authService;
    }

   public Server(){
       clients=new Vector<>();
       try {
           serverSocket=new ServerSocket(PORT);
           //авторизация
           authService = new BaseAuthService();
           authService.start();
           System.out.println("Сервер запущен,ждем подключений");
           while (true){
               socket=serverSocket.accept();
               clients.add(new ClientHandler(socket,this));
               System.out.println("Клиент подключился");
           }
       } catch (IOException e) {
           e.printStackTrace();
       }finally {
           try {
               serverSocket.close();
               socket.close();
           } catch (IOException e) {
               e.printStackTrace();
           }
       }

   }
    public void broadcast(String msg){
        for(ClientHandler c: clients){
            c.sendMessage(msg);
        }
    }
    public void unSubscribeMe(ClientHandler c){
        clients.remove(c);
    }
    public boolean isNickBusy(String nick){
        System.out.println(nick);
        for(ClientHandler c: clients){
            if(c.getName().equals(nick)) return true;
        }
        return false;
    }

    public void getCliensOnline(String text) {
        for(ClientHandler c: clients){
            text+=c.getName()+" ";
        }
        broadcast(text);
    }

    public void privateSend(String name, String text,ClientHandler clientHandler) {
        String tempText=clientHandler.getName()+" to "+name+" :"+text;
        for (ClientHandler c : clients) {
            if(c.getName().equals(name)){
                c.sendMessage(tempText);
            }
        }
        clientHandler.sendMessage(tempText);
    }
}
