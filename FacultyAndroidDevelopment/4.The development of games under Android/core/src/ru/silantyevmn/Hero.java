package ru.silantyevmn;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * ru.silantyevmn
 * Created by Михаил Силантьев on 21.12.2017.
 */
public class Hero {
    private Texture texture;
    private Vector2 position;
    private Vector2 velocity;
    private Map map;

    public Hero(Map map, float x, float y) {
        this.map = map;
        this.position = new Vector2(x, y);
        this.velocity = new Vector2(0, 0);
        texture = new Texture("runner.png");
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y, 50, 50, 100, 100, 1f, 1f, 0, 0, 0, 100, 100, false, false);
    }

    public void update(float dt) {
        //проверка на границы экрана
        if (position.y + 100 > 720) position.y = 720 - 100;
        if (position.x+20<0) position.x=-20;
        if (position.x+90>1280) position.x=1190;

        velocity.add(0, -400.0f * dt);
        boolean jumpOn=false;
        if (checkPosition(position.x + 20, position.x + 80, position.y, position.y)) {
            // если персонаж на земле
            velocity.y = 0;
            velocity.x = 0;
            jumpOn=true;
        }
        if (Gdx.input.justTouched()) {
            // для теста
            position.x = Gdx.input.getX();
            position.y = 720 - Gdx.input.getY();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) { // если нажали D идем вправо
            velocity.x = 200;
            if (checkPosition(position.x + 80, position.x + 80, position.y + 10, position.y + 90)) {
                velocity.x = 0;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) { // если нажали A идем влево
            velocity.x = -200;
            if (checkPosition(position.x + 20, position.x + 20, position.y + 10, position.y + 90)) {
                velocity.x = 0;
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if(jumpOn) {
                velocity.y = 300;
            }
        }
        position.mulAdd(velocity, dt);

    }

    private boolean checkPosition(float x1, float x2, float y1, float y2) {
        for (float i = x1; i < x2; i += 10) {
            if (!map.checkSpaceIsEmpty(i, y1)) return true;
        }
        for (float i = y1; i < y2; i += 10) {
            if (!map.checkSpaceIsEmpty(x1, i)) return true;
        }
        return false;
    }


}
