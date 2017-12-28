package ru.silantyevmn;

import com.badlogic.gdx.math.Vector2;

/**
 * ru.silantyevmn
 * Created by Михаил Силантьев on 28.12.2017.
 */
public class PowerUp {
    public enum Type{
        MONEY_10(0),MONEY_25(1),MONEY_50(2);
        int indexPosition;
        Type(int indexPosition){
            this.indexPosition=indexPosition;
        }
    }
    private Vector2 position;
    private boolean activity;
    private Type type;
    private float time;
    private float maxTime;

    public PowerUp(){
        this.position=new Vector2(0,0);
        this.activity=false;
        this.time=0.0f;
        this.maxTime=5.0f;
    }
    public void activate(float x,float y){
        activity=true;
        position.set(x,y);
        type=Type.values()[(int)Math.random()*Type.values().length];
    }
    public void deactivate(){
        activity=false;
    }

    public void update(float dt){
        time+=dt;
        if(time>maxTime) deactivate();
    }
}
