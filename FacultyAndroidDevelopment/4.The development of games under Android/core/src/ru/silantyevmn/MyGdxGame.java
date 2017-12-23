package ru.silantyevmn;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class MyGdxGame extends ApplicationAdapter {
    private SpriteBatch batch;
    private Map map;
    private Hero hero;
    private BitmapFont font48;

    @Override
    public void create() {
        batch = new SpriteBatch();
        map = new Map();
        map.generateMap();
        hero=new Hero(map,200,300);
        generateFonts();
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
        hero.renderGUI(batch, font48);
        batch.end();
    }

    public void generateFonts() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("zorque.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameters = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameters.size = 48;
        parameters.color = Color.WHITE;
        parameters.borderWidth = 1;
        parameters.borderColor = Color.BLACK;
        parameters.shadowOffsetX = 3;
        parameters.shadowOffsetY = 3;
        parameters.shadowColor = Color.BLACK;
        font48 = generator.generateFont(parameters);
        generator.dispose();
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
