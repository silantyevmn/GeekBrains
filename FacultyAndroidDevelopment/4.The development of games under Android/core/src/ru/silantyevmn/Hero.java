package ru.silantyevmn;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;

/**
 * ru.silantyevmn
 * Created by Михаил Силантьев on 21.12.2017.
 */
public class Hero {
    private final int RADIUS=35;
    private Texture texture;
    private TextureRegion[] regions;
    private Vector2 position;
    private Vector2 velocity;
    private Vector2 tempPosition;
    private Map map;
    private boolean right;

    private float animationTime;

    public Hero(Map map, float x, float y) {
        this.map = map;
        this.position = new Vector2(x, y);
        this.velocity = new Vector2(0, 0);
        this.tempPosition = new Vector2(0, 0);
        this.texture = new Texture("runner.png");
        this.regions = new TextureRegion(texture).split(100, 100)[0]; //разбиваем картинку на массив картинок для анимации
        this.right=true;

    }

    public void render(SpriteBatch batch) {
        //batch.draw(texture, position.x, position.y, 50, 50, 100, 100, 1f, 1f, 0, 0, 0, 100, 100, false, false);
        int frameIndex=getCurrentFrame();
        if (!right && !regions[frameIndex].isFlipX()) {
            regions[frameIndex].flip(true, false);
        }
        if (right && regions[frameIndex].isFlipX()) {
            regions[frameIndex].flip(true, false);
        }
        batch.draw(regions[frameIndex], position.x, position.y, 50, 50, 100, 100, 1, 1, 0);

    }

    public void update(float dt) {

        velocity.add(0, -400.0f * dt); //скорость падения
        velocity.x *= 0.6f; // уменьшаем скорость по х
        tempPosition.set(position);
        tempPosition.add(50, 50);
        if (Gdx.input.isKeyPressed(Input.Keys.D)) { // если нажали D идем вправо
            velocity.x = 200;
            right=true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) { // если нажали A идем влево
            velocity.x = -200;
            right=false;
        }
        // проверка столкновений персонажа по пикселям
        float len = velocity.len() * dt;
        float dx = velocity.x * dt / len;
        float dy = velocity.y * dt / len;
        for (int i = 0; i < len; i++) {
            tempPosition.y += dy;
            if (checkCollision(tempPosition)) {
                tempPosition.y -= dy;
                velocity.y = 0.0f;
            }
            tempPosition.x += dx;
            if (checkCollision(tempPosition)) {
                tempPosition.x -= dx;
                velocity.x = 0.0f;
            }
        }
        if (Math.abs(velocity.x) > 1.0f) {
            if (Math.abs(velocity.y) < 1.0f) {
                animationTime += (Math.abs(velocity.x) / 1800.0f);
            }
        } else {
            if (getCurrentFrame() > 0) {
                animationTime += dt * 50.0f;
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && velocity.y==0) { //если нажали пробел и персонаж не падает, тогда прыжок
                velocity.y = 300;
        }

        tempPosition.add(-50, -50);
        position.set(tempPosition);
        //hitArea.setPosition(position.x + 50, position.y + 50);
    }

    private int getCurrentFrame() {
        return (int) animationTime % 6;
    }

    private boolean checkCollision(Vector2 tempPosition) {
        for (float i = 0; i < 6.28f; i += 0.1f) {
            if (!map.checkSpaceIsEmpty(tempPosition.x + RADIUS * (float) Math.cos(i), tempPosition.y + RADIUS * (float) Math.sin(i))) {
                return true;
            }
        }
        return false;
    }



}
