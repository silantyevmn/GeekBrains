package ru.silantyevmn.game;

import com.badlogic.gdx.graphics.g2d.*;

/**
 * ru.silantyevmn.game
 * Created by Михаил Силантьев on 04.01.2018.
 */

public class BulletEmitter extends ObjectPool<Bullet> {
    private TextureRegion bulletTexture;

    @Override
    protected Bullet newObject() {
        return new Bullet();
    }

    public BulletEmitter(TextureRegion bulletsTexture, int size) {
        super(size);
        this.bulletTexture = bulletsTexture;
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < activeList.size(); i++) {
            batch.draw(bulletTexture, activeList.get(i).getPosition().x - 24, activeList.get(i).getPosition().y - 24, 24, 24, 48, 48, 0.7f, 0.7f, 0.0f);
        }
    }

    public void update(float dt) {
        for (int i = 0; i < activeList.size(); i++) {
            activeList.get(i).update(dt);
        }
    }

    public void setup(boolean isPlayersBullet, float x, float y, float vx, float vy) {
        Bullet b = getActiveElement();
        b.activate(isPlayersBullet, x, y, vx, vy);
    }
}
