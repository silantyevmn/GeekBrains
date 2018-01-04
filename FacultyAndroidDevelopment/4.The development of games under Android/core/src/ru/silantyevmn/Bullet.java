package ru.silantyevmn;

import com.badlogic.gdx.math.Vector2;

import java.awt.*;

/**
 * ru.silantyevmn
 * Created by Михаил Силантьев on 04.01.2018.
 */
public class Bullet implements Poolable {
    private boolean isPlayersBullet;
    private Vector2 position;
    private Vector2 velocity;
    private boolean active;

    public Vector2 getPosition() {
        return position;
    }

    public boolean isPlayersBullet() {

        return isPlayersBullet;
    }
    public boolean isActive(){ return active;}

    public Bullet(){
        this.position=new Vector2(0,0);
        this.velocity=new Vector2(0,0);
        this.active=false;
    }
    public void deactivate(){ this.active=false; }
    public void activate(boolean isPlayersBullet,float x,float y,float vx,float vy){
        this.isPlayersBullet=isPlayersBullet;
        position.set(x,y);
        velocity.set(vx,vy);
        active=true;
    }
    public void update(float dt){
        position.mulAdd(velocity,dt);
        if(position.x>1280 || position.x<0 || position.y<0 || position.y>720){
            deactivate();
        }
    }

}
