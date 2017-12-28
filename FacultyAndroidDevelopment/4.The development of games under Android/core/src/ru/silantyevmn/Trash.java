package ru.silantyevmn;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * ru.silantyevmn
 * Created by Михаил Силантьев on 23.12.2017.
 */
public class Trash {
    private final int RADIUS = 28;
    private TextureRegion texture;
    private Vector2 position;
    private Vector2 velocity;
    private Circle hitArea;
    private float scale;
    private float angle;
    private float speed;


    public Circle getHitArea() {
        return hitArea;
    }

    public Trash(TextureRegion texture) {
        this.texture = texture;
        this.position = new Vector2(0, 0);
        this.velocity = new Vector2(0, 0);
        this.hitArea = new Circle(position, RADIUS);
        this.speed=-300;
    }

    public void prepare() {
        position.set(MathUtils.random(0, 1280), MathUtils.random(1500, 5000));
        hitArea.setPosition(position);
        scale = MathUtils.random(0.6f, 0.8f);
        speed=scale/0.6f*-300;
        velocity.set(0, speed);
        hitArea.radius = RADIUS * scale;
        angle = MathUtils.random(0, 360);
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x - 32, position.y - 32, 32, 32, 64, 64, scale, scale, angle);
    }

    public void update(float dt) {
        angle+=dt*200*scale;
        position.mulAdd(velocity, dt);
        if (position.y < -100) {
            prepare();
        }
        hitArea.setPosition(position);
    }
}
