package ru.silantyevmn;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends ApplicationAdapter {
    private SpriteBatch batch;
    private Map map;
    private Hero hero;

    @Override
    public void create() {
        batch = new SpriteBatch();
        map = new Map();
        map.generateMap();
        hero=new Hero(map,200,300);
    }



    @Override
    public void render() {
        float dt = Gdx.graphics.getDeltaTime();
        update(dt);
        Gdx.gl.glClearColor(0.9f, 0.9f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        map.render(batch);
        hero.render(batch);
        batch.end();
    }

    public void update(float dt) {
        map.update(dt);
        hero.update(dt);
    }

    @Override
    public void dispose() {
        map.dispoce();
        batch.dispose();
    }
}
