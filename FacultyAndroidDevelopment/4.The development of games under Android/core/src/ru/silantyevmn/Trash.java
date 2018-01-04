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
    private int radius;
    private TextureRegion texture;
    private Vector2 position;
    private Vector2 velocity;
    private Circle hitArea;
    private float scale;
    private float angle;
    private float speed;
    private float width;
    private float height;


    public Circle getHitArea() {
        return hitArea;
    }

    public Trash(TextureRegion texture) {
        this.texture = texture;
        this.position = new Vector2(0, 0);
        this.velocity = new Vector2(0, 0);
        this.width=64;
        this.height=64;
        this.radius=(int)width/2;
        this.hitArea = new Circle(position, radius);
        this.speed=-300;
    }

    public void prepare() {
        position.set(MathUtils.random(0, 1280), MathUtils.random(1500, 5000));
        hitArea.setPosition(position);
        scale = MathUtils.random(0.6f, 0.8f);
        //radius*=scale;
        speed=scale/0.6f*-300;
        velocity.set(0, speed);
        hitArea.radius = radius;
        angle = MathUtils.random(0, 360);
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x - radius, position.y - radius, width/2, height/2, width, height, scale, scale, angle);
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
