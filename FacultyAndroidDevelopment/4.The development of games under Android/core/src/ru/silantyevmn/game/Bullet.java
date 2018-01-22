package ru.silantyevmn.game;

import com.badlogic.gdx.math.Vector2;
/**
 * ru.silantyevmn.game
 * Created by Михаил Силантьев on 04.01.2018.
 */

public class Bullet implements Poolable {
    private boolean isPlayersBullet;
    private Vector2 position;
    private Vector2 velocity;
    private boolean active;
    private float time;

    public boolean isPlayersBullet() {
        return isPlayersBullet;
    }

    public Vector2 getPosition() {
        return position;
    }

    public boolean isActive() {
        return active;
    }

    public Bullet() {
        this.position = new Vector2(0.0f, 0.0f);
        this.velocity = new Vector2(0.0f, 0.0f);
        this.active = false;
    }

    public void deactivate() {
        this.active = false;
    }

    public void activate(boolean isPlayersBullet, float x, float y, float vx, float vy) {
        this.isPlayersBullet = isPlayersBullet;
        position.set(x, y);
        velocity.set(vx, vy);
        active = true;
        time = 0.0f;
    }

    public void update(float dt) {
        position.mulAdd(velocity, dt);
        time += dt;
        if (time > 1.2f) {
            deactivate();
        }
    }
}
