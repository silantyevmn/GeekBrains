package ru.silantyevmn;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

/**
 * ru.silantyevmn
 * Created by Михаил Силантьев on 04.01.2018.
 */
public class Monster extends BaseUnit {
    private boolean activity;
    private float timeMonster;
    private float time;

    public boolean isActivity() {
        return this.activity;
    }

    public Monster(GameScreen gameScreen, Map map, TextureRegion original, float x, float y) {
        super(gameScreen, map, original, 100, 250.0f, 35, 3.0f, x, y, 100, 100);
        timeMonster = 3.0f;
        activate(x, y);
    }

    public void activate(float x, float y) {
        this.activity = true;
        position.set(x, y);
        hp = maxHp;
        time = 0;
    }

    public void deactivate() {
        this.activity = false;
    }

    @Override
    public void render(SpriteBatch batch) {
        if (isActivity()) {
            batch.setColor(Color.RED);
            super.render(batch);
            batch.setColor(Color.WHITE);
        }
    }

    @Override
    public void update(float dt) {
        //отчет времени на появление монстра
        if (!isActivity()) {
            time += dt;
            if (time > timeMonster) {
                time = 0;
                activate(MathUtils.random(700, 1100), MathUtils.random(300, 400));
            }
        } else {
            fire(dt, false);
            if (Math.abs(gameScreen.getHero().position.x - position.x) > 100.0f) {
                if (gameScreen.getHero().position.x > position.x) { // если персонаж правее идем вправо
                    moveRight();
                }
                if (gameScreen.getHero().position.x < position.x) { // если персонаж левее идем влево
                    moveLeft();
                }
            }
            super.update(dt);
            if (Math.abs(gameScreen.getHero().position.x - position.x) > 100.0f) {
                if (Math.abs(velocity.x) < 0.1f) { //если препятствие, тогда прыжок
                    jump();
                }
            }
        }


    }

}

