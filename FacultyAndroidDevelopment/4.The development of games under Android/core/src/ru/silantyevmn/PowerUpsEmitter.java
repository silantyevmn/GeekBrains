package ru.silantyevmn;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * ru.silantyevmn
 * Created by Михаил Силантьев on 28.12.2017.
 */
public class PowerUpsEmitter {
    private PowerUp[] powerUps;
    private TextureRegion[] textureRegion;

    public PowerUp[] getPowerUps() {
        return powerUps;
    }

    public PowerUpsEmitter(){
        this.powerUps=new PowerUp[10];
        Texture texture=new Texture("money.png");
        textureRegion=new TextureRegion(texture).split(32,32)[0];
        for (int i = 0; i < powerUps.length; i++) {
            powerUps[i]=new PowerUp();
        }
    }
    public void render(SpriteBatch batch){
        for (int i = 0; i < powerUps.length; i++) {
            if(powerUps[i].isActivity()){
                batch.draw(textureRegion[powerUps[i].getType().getImagePosition()],powerUps[i].getPosition().x,powerUps[i].getPosition().y);
            }

        }
    }
    public void createPowerUp(float x,float y,float probability){
        if(Math.random()<probability){
            for (int i = 0; i < powerUps.length; i++) {
                if(!powerUps[i].isActivity()){
                    powerUps[i].activate(x,y);
                    break;
                }

            }
        }
    }
    public void update(float dt){
        for (int i = 0; i < powerUps.length; i++) {
            if(powerUps[i].isActivity()){
                powerUps[i].update(dt);
            }

        }
    }
}