package ru.silantyevmn.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ru.silantyevmn.game.screens.*;

/**
 * ru.silantyevmn.game
 * Created by Михаил Силантьев on 04.01.2018.
 */
public class Monster extends BaseUnit {
    private boolean active;

    public boolean isActive() {
        return active;
    }

    public Monster(GameScreen gameScreen, Map map, float x, float y) {
        super(gameScreen, map, 100, 300.0f, 1.8f, x, y, 100, 100);
        this.type = Type.Hero;
        this.active = false;
        this.afterLoad(gameScreen);
    }

    @Override
    public void update(float dt) {
        fire(dt, false);
        if (Math.abs(gameScreen.getHero().getCenterX() - getCenterX()) > 100.0f) {
            if (gameScreen.getHero().getCenterX() < getCenterX()) {
                moveLeft();
            }
            if (gameScreen.getHero().getCenterX() > getCenterX()) {
                moveRight();
            }
        }
        super.update(dt);
        if (Math.abs(gameScreen.getHero().getCenterX() - getCenterX()) > 100.0f) {
            if (Math.abs(velocity.x) < 0.1f) {
                jump();
            }
        }
    }

    public void deactivate() {
        active = false;
    }

    public void activate(float x, float y) {
        active = true;
        hitArea.setPosition(x, y);
        hp = maxHp;
    }

    @Override
    public void destroy() {
        deactivate();
    }

    @Override
    public void render(SpriteBatch batch) {
       //batch.setColor(Color.RED);
       super.render(batch);
      /* batch.setColor(Color.RED);
       batch.setColor(Color.WHITE);*/
    }
}
