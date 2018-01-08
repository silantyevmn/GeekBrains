package ru.silantyevmn;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;

/**
 * ru.silantyevmn
 * Created by Михаил Силантьев on 21.12.2017.
 */
public class Hero extends BaseUnit {
    private int money;

    public Hero(GameScreen gameScreen, Map map, TextureRegion original, float x, float y) {
        super(gameScreen, map, original, 100, 400, 40, 0.3f, x, y, 100, 100);
        this.money = 0;
    }

    public void addMoney(int amount) {
        money += amount;
    }

    @Override
    public void update(float dt) {
        if (Gdx.input.isTouched()) {
            fire(dt, true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) { // если нажали D идем вправо
            moveRight();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) { // если нажали A идем влево
            moveLeft();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) { //если нажали пробел и персонаж не падает, тогда прыжок
            jump();
        }
        super.update(dt);
    }

    public void renderGUI(SpriteBatch batch, BitmapFont font48) {
        font48.draw(batch, "HP: " + hp + " / " + maxHp + "\nMoney:" + money, 20, 700);
    }

}
