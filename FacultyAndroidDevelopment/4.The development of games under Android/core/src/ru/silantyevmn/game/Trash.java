package ru.silantyevmn.game;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;

/**
 * ru.silantyevmn.game
 * Created by Михаил Силантьев on 04.01.2018.
 */

public class Trash {
    private Vector2 position;
    private Vector2 velocity;
    private TextureRegion texture;
    private Rectangle hitArea;
    private float scale;
    private float angle;
    private float speed;

    public Vector2 getPosition() {
        return position;
    }

    public Rectangle getHitArea() {
        return hitArea;
    }

    public Trash(TextureRegion texture) {
        this.texture = texture;
        this.position = new Vector2(0, 0);
        this.velocity = new Vector2(0, 0);
        this.hitArea = new Rectangle(0, 0, 0, 0);
        this.speed=0;
    }

    public void prepare(int heroPositionX) {
        position.set(MathUtils.random(heroPositionX - RunnerGame.WIDTH, heroPositionX + RunnerGame.WIDTH), MathUtils.random(1500, 5000));
        //velocity.set(0, -speed);
        hitArea.setPosition(position);
        scale = MathUtils.random(0.6f, 0.8f);
        speed=scale/0.6f*400; //скорость падения камня
        velocity.set(0, -speed);
        hitArea.width = 28 * scale;
        hitArea.height = 28 * scale;
        angle = 0;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x - 32, position.y - 32, 32, 32, 64, 64, scale, scale, angle);
    }

    public void update(float dt) {
        position.mulAdd(velocity, dt);
        hitArea.setPosition(position);
        hitArea.x += 2 * scale;
        hitArea.y += 2 * scale;
        angle+=dt*200*scale; //крутим камни
    }
}
