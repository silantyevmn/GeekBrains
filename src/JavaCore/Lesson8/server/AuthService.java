package JavaCore.Lesson8.server;

/**
 * JavaCore.Lesson8.server
 * Created by Михаил Силантьев on 30.10.2017.
 */
public interface AuthService {
    void start();
    void stop();
    String getNickByLoginPass(String login, String pass);
}
