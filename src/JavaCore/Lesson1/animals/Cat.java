package JavaCore.Lesson1.animals;

/**
 * JavaCore.Lesson1
 * Created by Михаил Силантьев on 04.10.2017.
 */
public class Cat extends Animal {
    public Cat(String name, int maxRunDistance, int maxJumpHeight, int maxSwimDistance){
        super(name, maxRunDistance, maxJumpHeight, maxSwimDistance);
        this.type = "Cat";
    }
}
