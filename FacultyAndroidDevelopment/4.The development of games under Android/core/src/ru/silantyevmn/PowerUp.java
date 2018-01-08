package ru.silantyevmn;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * ru.silantyevmn
 * Created by Михаил Силантьев on 28.12.2017.
 */
public class PowerUp {
    public enum Type {
        MONEY_10(0), MONEY_25(1), MONEY_50(2), MED_KIT(3);
        private int imagePosition;

        public int getImagePosition() {
            return imagePosition;
        }

        Type(int imagePosition) {
            this.imagePosition = imagePosition;
        }
    }

    private Vector2 position;
    private float startY;
    private boolean activity;
    private Type type;
    private float time;
    private float maxTime;

    public PowerUp() {
        this.position = new Vector2(0, 0);
        this.activity = false;
        this.time = 0.0f;
        this.maxTime = 3.0f;
    }

    public Type getType() {
        return type;
    }

    public Vector2 getPosition() {
        return position;
    }

    public boolean isActivity() {
        return activity;
    }

    public void activate(float x, float y) {
        activity = true;
        time = 0.0f;
        position.set(x, y);
        startY = y;
        type = Type.values()[MathUtils.random(0, Type.values().length - 1)];
        if(type==Type.MED_KIT){ // аптечка появляется реже
            if(Math.random()>0.3f) type = Type.values()[MathUtils.random(0, Type.values().length - 2)];
        }
    }

    public void deactivate() {
        activity = false;
    }

    public void update(float dt) {
        time += dt;
        position.y = startY + 15 * (float) Math.sin(time * 3.0f);
        if (time > maxTime) deactivate();
    }

    public void use(Hero hero) {
        switch (type) {
            case MONEY_10:
                hero.addMoney(10);
                break;
            case MONEY_25:
                hero.addMoney(25);
                break;
            case MONEY_50:
                hero.addMoney(50);
                break;
            case MED_KIT:
                hero.addHp(50);
                break;
        }
    }
}
