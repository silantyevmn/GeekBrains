package ru.silantyevmn.game;

import com.badlogic.gdx.graphics.g2d.*;

/**
 * ru.silantyevmn.game
 * Created by Михаил Силантьев on 04.01.2018.
 */

public class PowerUpsEmitter {
    private PowerUp[] powerUps;
    private TextureRegion[] textureRegions;

    public PowerUp[] getPowerUps() {
        return powerUps;
    }

    public PowerUpsEmitter(TextureRegion texture) {
        textureRegions = new TextureRegion(texture).split(32, 32)[0];
        powerUps = new PowerUp[20];
        for (int i = 0; i < powerUps.length; i++) {
            powerUps[i] = new PowerUp();
        }
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < powerUps.length; i++) {
            if (powerUps[i].isActive()) {
                batch.draw(textureRegions[powerUps[i].getType().getImagePosition()], powerUps[i].getPosition().x - 16, powerUps[i].getPosition().y - 16);
            }
        }
    }

    public void tryToCreatePowerUp(float x, float y, float probability) {
        if (Math.random() < probability) {
            for (int i = 0; i < powerUps.length; i++) {
                if (!powerUps[i].isActive()) {
                    powerUps[i].activate(x, y);
                    break;
                }
            }
        }
    }

    public void update(float dt) {
        for (int i = 0; i < powerUps.length; i++) {
            if (powerUps[i].isActive()) {
                powerUps[i].update(dt);
            }
        }
    }
}
