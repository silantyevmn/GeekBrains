package ru.silantyevmn.game;

import com.badlogic.gdx.math.Vector2;

/**
 * ru.silantyevmn.game
 * Created by Михаил Силантьев on 04.01.2018.
 */

public class PowerUp {
    public enum Type {
        MONEY_10(0), MONEY_25(1), MONEY_50(2); //, MONEY_100, MEDKIT;

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
    private boolean active;
    private Type type;
    private float time;
    private float maxTime;

    public Type getType() {
        return type;
    }

    public Vector2 getPosition() {
        return position;
    }

    public boolean isActive() {
        return active;
    }

    public PowerUp() {
        this.position = new Vector2(0, 0);
        this.active = false;
        this.type = Type.MONEY_10;
        this.time = 0.0f;
        this.maxTime = 3.0f;
    }

    public void deactivate() {
        active = false;
    }

    public void activate(float x, float y) {
        active = true;
        position.set(x, y);
        startY = y;
        time = 0.0f;
        type = Type.values()[(int)(Math.random() * Type.values().length)];
    }

    public void update(float dt) {
        time += dt;
        position.y = startY + 15 * (float)Math.sin(time * 3.0f);
        if (time > maxTime) {
            deactivate();
        }
    }

    public void use(Hero hero) {
        switch (type) {
            case MONEY_10:
                hero.addCoins(10);
                break;
            case MONEY_25:
                hero.addCoins(25);
                break;
            case MONEY_50:
                hero.addCoins(50);
                break;
        }
    }
}
