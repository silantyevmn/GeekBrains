package JavaCore.Lesson7.server;

/**
 * JavaCore.Lesson7.server
 * Created by Михаил Силантьев on 24.10.2017.
 */
public interface AuthService {
    void start();
    void stop();
    String getNickByLoginPass(String login, String pass);
}