package JavaCore.Lesson7.server;

import java.util.ArrayList;

/**
 * JavaCore.Lesson7.server
 * Created by Михаил Силантьев on 24.10.2017.
 */
public class BaseAuthService implements AuthService{
    private ArrayList<Person> persons;

    private class Person {
        private String login;
        private String pass;
        private String nick;

        public Person(String login, String pass, String nick) {
            this.login = login;
            this.pass = pass;
            this.nick = nick;
        }
    }

    public BaseAuthService(){
        persons=new ArrayList<>();
        persons.add(new Person("nik1","123","nik1"));
        persons.add(new Person("nik2","123","nik2"));
        persons.add(new Person("nik3","123","nik3"));
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public String getNickByLoginPass(String login, String pass) {
        for(Person p: persons){
            if(p.login.equals(login) && p.pass.equals(pass)) return p.nick;
        }
        return null;
    }
}
