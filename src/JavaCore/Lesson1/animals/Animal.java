package JavaCore.Lesson1.animals;

import JavaCore.Lesson1.logic.iChallenagable;

/**
 * JavaCore.Lesson1
 * Created by Михаил Силантьев on 04.10.2017.
 */
public class Animal implements iChallenagable {
    protected int maxRunDistance;
    protected int maxJumpHeight;
    protected int maxSwimDistance;
    protected boolean onDistance;

    protected String name;
    protected String type;

    public boolean isOnDistance() {
        return onDistance;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Animal(String name, int maxRunDistance, int maxJumpHeight, int maxSwimDistance){
        this.name = name;
        this.maxRunDistance = maxRunDistance;
        this.maxJumpHeight = maxJumpHeight;
        this.maxSwimDistance = maxSwimDistance;
        this.onDistance = true;
    }

    @Override
    public void run(int distance) {
        System.out.printf("\nУчастник %s %s дальность бега %dм участок %dм - %s",type,name,maxRunDistance,distance,(distance<=maxRunDistance)?
                "преодолел":"не преодолел");
        if(distance>maxRunDistance) this.onDistance = false;
//        if(distance <= this.maxRunDistance) System.out.println(this.type + " " + this.name + " " + " пробежал дистанцию");
//        else{
//            this.onDistance = false;
//            System.out.println(this.type + " " + this.name + " не пробежал дистанцию");
//        }
    }

    @Override
    public void swim(int distance) {
        System.out.printf("\nУчастник %s %s дальность плавания %dм участок %dм - %s %s",type,name,maxSwimDistance,distance,(distance<=maxSwimDistance)?
                "преодолел":"не проплыл",maxSwimDistance==0 ? ",плавать не умеет":"" );
        if(distance>maxSwimDistance) this.onDistance = false;
    }

    @Override
    public void jump(int height) {
        System.out.printf("\nУчастник %s %s дальность прыжка %dм участок %dм - %s",type,name,maxJumpHeight,height,(height<=maxJumpHeight)?
                "перепрыгнул":"не перепрыгнул");
        if(height > this.maxJumpHeight) this.onDistance = false;
    }

    @Override
    public void printResult() {
        if(onDistance) System.out.println(this.type + " " + this.name + " прошел дистанцию");
        else System.out.println(this.type + " " + this.name + " не прошел дистанцию");
    }

    @Override
    public void print() {
        System.out.println(type+" "+name+" бег-"+maxRunDistance+",прыжок-"+maxJumpHeight+",плавание-"+maxSwimDistance);
    }
}
