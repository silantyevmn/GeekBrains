package ru.silantyevmn;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * ru.silantyevmn
 * Created by Михаил Силантьев on 21.12.2017.
 */
public class Map {
    public final char SYM_GROUNG = 'g';
    private TextureRegion textureGround;
    private TextureRegion textureSnow;
    private char[][] data;
    private Snow[] snows;
    private final boolean SNOW_ENABLE=false;
    private final int SHOW_COUNT=100;

    public Map(TextureRegion imageGroung,TextureRegion imageSnow) {
        textureGround = imageGroung;
        textureSnow = imageSnow;
        data = new char[32][18];
        snows = new Snow[SHOW_COUNT];
        if(!SNOW_ENABLE){
            snows=new Snow[0];
        }

    }

    public boolean checkSpaceIsEmpty(float x, float y) {
        if (x < 0 || x > Gdx.graphics.getWidth()) return false;
        int cellX = (int) (x / 40);
        int cellY = (int) (y / 40);
        return IsCellEmpty(cellX, cellY);
    }

    private boolean IsCellEmpty(int cellX, int cellY) {
        if (data[cellX][cellY] == SYM_GROUNG) {
            return false;
        }
        return true;
    }

    public void generateMap() {
        int height = 3;// высота
        int position = 0;
        fillMap(0, 3, height);
        position = 4;
        while (position < 32) {
            int len = MathUtils.random(2, 4); //длина земли 3-5 кубиков
            height += MathUtils.random(-2, 2); //высота от -2 до 2
            if (height < 1) height = 1;
            if (height > 4) height = 4;
            fillMap(position, position + len - 1, height);
            position += len;
        }
//        //заполняем массив препятствиями сверху
//        fillBridge(0,3,7);
//        position=8;
//        while (position<32){
//            int len= MathUtils.random(1,4); //длина кубиков
//            height=1;
//            while (!IsCellEmpty(position,height)){
//                height++;
//            }
//            fillBridge(position,position+len-1,height+3);
//            position+=len+4;
//        }
        for (int i = 0; i < snows.length; i++) {
            snows[i] = new Snow(textureSnow);
            snows[i].recreate();
        }


    }

    private void fillBridge(int x1, int x2, int height) {
        if (x2 > 31) x2 = 31;
        for (int i = x1; i <= x2; i++) {
            data[i][height] = SYM_GROUNG;
        }
    }

    private void fillMap(int x1, int x2, int height) {
        if (x2 > 31) x2 = 31;
        for (int i = x1; i <= x2; i++) {
            for (int j = 0; j < height; j++) {
                data[i][j] = SYM_GROUNG;
            }

        }
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (data[i][j] == SYM_GROUNG) {
                    batch.draw(textureGround, i * 40, j * 40);
                }
            }
        }
        for (int i = 0; i < snows.length; i++) {
            snows[i].render(batch);
        }
    }

    public void update(float dt) {
        for (int i = 0; i < snows.length; i++) {
            snows[i].update(dt);
        }
    }

    public void dispoce() {

    }
}
