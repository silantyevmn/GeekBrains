package ru.silantyevmn.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import ru.silantyevmn.game.screens.*;

import java.io.Serializable;

/**
 * ru.silantyevmn.game
 * Created by Михаил Силантьев on 04.01.2018.
 */

public class MonsterEmitter implements Serializable {
    private transient GameScreen gameScreen;
    private Monster[] monsters;
    private float factoryCurrentTimer;
    private float factoryRate;

    public Monster[] getMonsters() {
        return monsters;
    }

    public MonsterEmitter(GameScreen gameScreen, int poolSize, float factoryRate) {
        this.gameScreen = gameScreen;
        this.monsters = new Monster[poolSize];
        this.factoryRate = factoryRate;
        for (int i = 0; i < monsters.length; i++) {
            this.monsters[i] = new Monster(gameScreen, gameScreen.getMap(), 0, 0);
        }
    }

    public void afterLoad(GameScreen gameScreen) {
        for (int i = 0; i < monsters.length; i++) {
            monsters[i].afterLoad(gameScreen);
        }
        this.gameScreen = gameScreen;
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < monsters.length; i++) {
            if (monsters[i].isActive()) {
                monsters[i].render(batch);
            }
        }
    }

    public void createMonster(int x, int y) {
        for (int i = 0; i < monsters.length; i++) {
            if (!monsters[i].isActive()) {
                monsters[i].activate(x, y);
                break;
            }
        }
    }

    public void setBlocks() {
        for (int i = 0; i < monsters.length; i++) {
            if (monsters[i].isActive()) {
                monsters[i].setBlock();
            }
        }
    }

    public void update(Rectangle activeRect, float dt) {
        factoryCurrentTimer += dt;
        if (factoryCurrentTimer > factoryRate) {
            factoryCurrentTimer -= factoryRate;
            createMonster(MathUtils.random(0, gameScreen.getMap().getEndOfWorldX()), 500);
        }
        for (int i = 0; i < monsters.length; i++) {
           if (monsters[i].isActive() && activeRect.contains(monsters[i].getCenterX(), monsters[i].getCenterY())) {
           // if (monsters[i].isActive()) {
                monsters[i].update(dt);
            }
        }
    }
}
