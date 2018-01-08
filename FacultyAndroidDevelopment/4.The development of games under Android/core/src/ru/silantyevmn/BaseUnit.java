package ru.silantyevmn;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

/**
 * ru.silantyevmn
 * Created by Михаил Силантьев on 04.01.2018.
 */
public class BaseUnit {
    protected GameScreen gameScreen;
    protected TextureRegion[] regions;
    protected Vector2 position;
    protected Vector2 velocity;
    protected Vector2 tempPosition;
    protected Map map;
    protected boolean right;
    protected int maxHp;
    protected int hp;
    protected float firePressTimer;
    protected float timeFire;
    protected int width;
    protected int height;
    protected float speed;
    protected float radius;
    private Circle hitArea;
    private float animationTime;

    public int getHp() {
        return hp;
    }

    public Circle getHitArea() {
        return hitArea;
    }

    public BaseUnit(GameScreen gameScreen,Map map,TextureRegion original,int maxHp,float speed,float radius, float timeFire,float x, float y,int width,int height) {
        this.gameScreen=gameScreen;
        this.map = map;
        this.position = new Vector2(x, y);
        this.velocity = new Vector2(0, 0);
        this.tempPosition = new Vector2(0, 0);
        this.width=width;
        this.height=height;
        this.regions = new TextureRegion(original).split(width, height)[0]; //разбиваем картинку на массив картинок для анимации
        this.right = true;
        this.maxHp = maxHp;
        this.hp = this.maxHp;
        this.radius=radius;
        this.hitArea = new Circle(position, radius);
        this.timeFire=timeFire;
        this.speed=speed;
    }
    public void moveLeft(){
        velocity.x = -speed;
        right = false;
    }
    public void moveRight(){
        velocity.x = speed;
        right = true;
    }
    public void jump(){
        if (Math.abs(velocity.y)<0.1f) { // прыжок
            velocity.y = speed;
        }
    }
    public void fire(float dt,boolean isPlayerFire){
        this.firePressTimer+=dt;
        if(firePressTimer>timeFire){
            firePressTimer=0;
            float bulletVelX=speed*1.4f; //скорость пули
            if(!right) bulletVelX*=-1;
            gameScreen.getBulletEmitter().setup(isPlayerFire,position.x+75,position.y+50,bulletVelX,0);
            gameScreen.getSoundFire().play(); //звук при выстреле
        }
    }

    public void render(SpriteBatch batch) {
        int frameIndex = getCurrentFrame();
        if (!right && !regions[frameIndex].isFlipX()) {
            regions[frameIndex].flip(true, false);
        }
        if (right && regions[frameIndex].isFlipX()) {
            regions[frameIndex].flip(true, false);
        }
        batch.draw(regions[frameIndex], position.x, position.y, width/2, height/2, width, height, 1, 1, 0);
    }

    public void update(float dt) {
        velocity.add(0, -400.0f * dt); //скорость падения
        velocity.x *= 0.6f; // уменьшаем скорость по х
        tempPosition.set(position);
        tempPosition.add(width/2, height/2);

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

        hitArea.setPosition(tempPosition); //обновление окружности персонажа для столкновений с врагами
        tempPosition.add(-width/2, -height/2);
        position.set(tempPosition);
    }

    public int getCurrentFrame() {
        return (int) animationTime % regions.length;
    }

    private boolean checkCollision(Vector2 tempPosition) { //стандартная проверка столкновений(окружность вокруг персонажа + radius)
        for (float i = 0; i < 6.28f; i += 0.1f) {
            if (!map.checkSpaceIsEmpty(tempPosition.x + hitArea.radius * (float) Math.cos(i), tempPosition.y + hitArea.radius * (float) Math.sin(i))) {
                return true;
            }
        }
        return false;
    }

    public void takeDamage(int dmg) {
        hp -= dmg;
    }

    public void addHp(int hpKit){
        hp+=hpKit;
        if(hp>maxHp) hp=maxHp;
    }
}
