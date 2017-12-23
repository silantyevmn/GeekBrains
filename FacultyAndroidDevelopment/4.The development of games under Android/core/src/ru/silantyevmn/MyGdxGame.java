package ru.silantyevmn;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class MyGdxGame extends ApplicationAdapter {
    private SpriteBatch batch;
    private Map map;
    private Hero hero;
    private BitmapFont font48;
    private BitmapFont font96;
    private Trash[] trashes;
    private boolean gameOver;
    Texture textureAsteroid;

    @Override
    public void create() {
        gameOver = false;
        batch = new SpriteBatch();
        map = new Map();
        map.generateMap();
        hero = new Hero(map, 200, 300);
        textureAsteroid = new Texture("asteroid64.png");
        trashes = new Trash[50];
        for (int i = 0; i < trashes.length; i++) {
            trashes[i] = new Trash(textureAsteroid);
            trashes[i].prepare();
        }
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
        for (int i = 0; i < trashes.length; i++) {
            trashes[i].render(batch);
        }
        hero.renderGUI(batch, font48);
        if (gameOver) {
            font96.draw(batch, "Game Over", 330, 400);
        }
        batch.end();
    }

    public void update(float dt) {
        if (hero.getHp() == 0) gameOver = true;
        if (!gameOver) {
            map.update(dt);
            hero.update(dt);
            for (int i = 0; i < trashes.length; i++) {
                trashes[i].update(dt);
                if (hero.getHitArea().overlaps(trashes[i].getHitArea())) {
                    trashes[i].prepare();
                    hero.takeDamage(5);
                }
            }
        } else if(Gdx.input.justTouched()) restart();
    }

    private void restart() {
        gameOver=false;
        map = new Map();
        map.generateMap();
        hero = new Hero(map, 200, 300);
        trashes = new Trash[50];
        for (int i = 0; i < trashes.length; i++) {
            trashes[i] = new Trash(textureAsteroid);
            trashes[i].prepare();
        }
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
        parameters.size = 96;
        font96 = generator.generateFont(parameters);
        generator.dispose();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
