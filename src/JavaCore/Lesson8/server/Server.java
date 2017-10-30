package JavaCore.Lesson8.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Vector;
import java.util.concurrent.TimeoutException;

/**
 * JavaCore.Lesson8.server
 * Created by Михаил Силантьев on 30.10.2017.
 */
public class Server {
   private final int PORT=8189;
   private final int timeout=20000;

   Vector<ClientHandler> clients;
    ServerSocket serverSocket=null;
    Socket socket=null;
    AuthService authService;
    public int getTimeout(){
        return timeout;
    }
    public AuthService getAuthService(){
        return authService;
    }

   public Server(){
       clients=new Vector<>();
       try {
           serverSocket = new ServerSocket(PORT);
           //авторизация
           authService = new BaseAuthService();
           authService.start();
           System.out.println("Сервер запущен,ждем подключений");
           while (true) {
               socket = serverSocket.accept();
               clients.add(new ClientHandler(socket, this));
               System.out.println("Клиент подключился");
           }
       }catch (IOException e) {
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
            if(c.getName().equals("noname")) continue;
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
            if(c.getName().equals("noname")) continue;
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
