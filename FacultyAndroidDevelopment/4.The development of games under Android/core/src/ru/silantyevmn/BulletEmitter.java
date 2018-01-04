package ru.silantyevmn;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * ru.silantyevmn
 * Created by Михаил Силантьев on 04.01.2018.
 */
public class BulletEmitter extends ObjectPool<Bullet>{
    private TextureRegion bulletTexture;
    @Override
    protected Bullet newObject() {
        return new Bullet();
    }

    public BulletEmitter(TextureRegion bulletTexture,int size){
        super(size);
        this.bulletTexture=bulletTexture;

    }
    public void render(SpriteBatch batch){
        for (int i = 0; i < activeList.size(); i++) {
            batch.draw(bulletTexture,activeList.get(i).getPosition().x-16,activeList.get(i).getPosition().y-16,16,16,32,32,2f,2f,0);
        }
    }
    public void update(float dt){
        for (int i = 0; i < activeList.size(); i++) {
             activeList.get(i).update(dt);
        }
    }
    public void setup(boolean isPlayersBullet,float x,float y,float vx,float vy){
        Bullet b=getActiveElement();
        b.activate(isPlayersBullet,x,y,vx,vy);
    }
}
