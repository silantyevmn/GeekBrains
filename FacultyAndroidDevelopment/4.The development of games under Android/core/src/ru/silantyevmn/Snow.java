package ru.silantyevmn;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * ru.silantyevmn
 * Created by Михаил Силантьев on 28.12.2017.
 */
public class Snow {
    private Vector2 position;
    private Vector2 velocity;
    private Texture texture;

    public Snow(Texture texture) {
        this.position = new Vector2(0, 0);
        this.velocity = new Vector2(0, 0);
        this.texture=texture;
    }

    public void recreate() {
        this.position.set(MathUtils.random(0, 1280), MathUtils.random(740, 1480));
        this.velocity.set(MathUtils.random(-50, 50), MathUtils.random(-200, -50));
    }

    public void render(SpriteBatch batch){
        batch.draw(texture,position.x-8,position.y-8);
    }
    public void update(float dt) {
        this.position.mulAdd(velocity, dt);
        if (position.x > 1280) position.x = 0;
        if (position.x < 0) position.x = 1280;
        if (position.y < 0) recreate();
    }
}
