package ru.silantyevmn;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

/**
 * ru.silantyevmn
 * Created by Михаил Силантьев on 21.12.2017.
 */
public class Map {
    public final char SYM_GROUNG='g';
    private Texture textureGround;
    private char[][] data;

    public Map() {
        textureGround=new Texture("ground.png");
        data =new char[32][18];
    }

    public boolean checkSpaceIsEmpty(float x,float y){
        if (x < 0 || x > Gdx.graphics.getWidth()) return false;
        int cellX=(int)(x/40);
        int cellY=(int)(y/40);
        return IsCellEmpty(cellX,cellY);
    }

    private boolean IsCellEmpty(int cellX, int cellY) {
        if(data[cellX][cellY]==SYM_GROUNG){
            return false;
        }
        return true;
    }

    public void generateMap() {
        int height=3;// высота
        int position=0;
        fillMap(0,3,height);
        position=4;
        while (position<32){
            int len= MathUtils.random(3,5); //длина земли 3-5 кубиков
            height+=MathUtils.random(-2,2); //высота от -2 до 2
            if(height<1) height=1;
            if(height>5) height=5;
            fillMap(position,position+len-1,height);
            position+=len;
        }
    }

    private  void fillMap(int x1,int x2,int height){
        if(x2>31) x2=31;
        for (int i = x1; i <=x2; i++) {
            for (int j = 0; j < height; j++) {
                data[i][j]=SYM_GROUNG;
            }

        }
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if(data[i][j]==SYM_GROUNG){
                    batch.draw(textureGround,i*40,j*40);
                }
            }
        }
    }

    public void dispoce() {
        textureGround.dispose();
    }

    public void update(float dt) {

    }
}
