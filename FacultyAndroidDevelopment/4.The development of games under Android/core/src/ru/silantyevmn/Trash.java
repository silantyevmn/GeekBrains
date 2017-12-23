package ru.silantyevmn;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * ru.silantyevmn
 * Created by Михаил Силантьев on 23.12.2017.
 */
public class Trash {
    private final int RADIUS=28;
    private Texture texture;
    private Vector2 position;
    private Vector2 velocity;
    private Circle hitArea;
    private float scale;
    private float angle;

    public Circle getHitArea() {
        return hitArea;
    }

    public Trash(Texture texture){
        this.texture=texture;
        this.position=new Vector2(0,0);
        this.velocity=new Vector2(0,0);
        this.hitArea=new Circle(position,RADIUS);
    }

    public void prepare() {
        position.set(MathUtils.random(0, 1280), MathUtils.random(1500, 5000));
        velocity.set(0, -300.0f);
        hitArea.setPosition(position);
        scale = MathUtils.random(0.6f, 0.8f);
        hitArea.radius = RADIUS * scale;
        angle = MathUtils.random(0, 360);
    }

    public void render(SpriteBatch batch){
        batch.draw(texture, position.x - 32, position.y - 32, 32, 32, 64, 64, scale, scale, angle, 0, 0, 64, 64, false, false);
    }

    public void update(float dt){
        position.mulAdd(velocity, dt);
        if (position.y < -100) {
            prepare();
        }
        hitArea.setPosition(position);
    }
}
