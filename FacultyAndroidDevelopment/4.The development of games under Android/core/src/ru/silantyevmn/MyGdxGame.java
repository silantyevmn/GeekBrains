package ru.silantyevmn;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;

public class MyGdxGame extends ApplicationAdapter {
    private SpriteBatch batch;
    private Map map;
    private Hero hero;
    private BitmapFont font24;
    private BitmapFont font48;
    private Trash[] trashes;
    private boolean gameOver;
    private Texture textureAsteroid;
    private PowerUpsEmitter powerUpsEmitter;
    private int counter;

    @Override
    public void create() {
        gameOver = false;
        batch = new SpriteBatch();
        map = new Map();
        map.generateMap();
        hero = new Hero(map, 200, 300);
        textureAsteroid = new Texture("asteroid64.png");
        trashes = new Trash[25];
        for (int i = 0; i < trashes.length; i++) {
            trashes[i] = new Trash(textureAsteroid);
            trashes[i].prepare();
        }
        generateFonts();
        powerUpsEmitter = new PowerUpsEmitter();
        counter = 0;
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
        powerUpsEmitter.render(batch);
        hero.renderGUI(batch, font24);
        if (gameOver) {
            font48.draw(batch, "Game Over", 330, 400);
        }
        batch.end();
    }

    public void update(float dt) {
        if (hero.getHp() == 0) gameOver = true;
        if (!gameOver) {
            counter++;
            if (counter % 50 == 0) {
                powerUpsEmitter.createPowerUp(MathUtils.random(0, 1280), MathUtils.random(200, 250), 1.0f);
            }
            map.update(dt);
            hero.update(dt);
            powerUpsEmitter.update(dt);
            for (int i = 0; i < trashes.length; i++) {
                trashes[i].update(dt);
                if (hero.getHitArea().overlaps(trashes[i].getHitArea())) {
                    trashes[i].prepare();
                    hero.takeDamage(5);
                }
            }
            for (int i = 0; i < powerUpsEmitter.getPowerUps().length; i++) {
                if (hero.getHitArea().contains(powerUpsEmitter.getPowerUps()[i].getPosition()) && powerUpsEmitter.getPowerUps()[i].isActivity()) {
                    powerUpsEmitter.getPowerUps()[i].use(hero);
                    powerUpsEmitter.getPowerUps()[i].deactivate();
                }

            }
        } else if (Gdx.input.justTouched()) restart();
    }

    private void restart() {
        gameOver = false;
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
        parameters.size = 24;
        parameters.color = Color.WHITE;
        parameters.borderWidth = 1;
        parameters.borderColor = Color.BLACK;
        parameters.shadowOffsetX = 3;
        parameters.shadowOffsetY = 3;
        parameters.shadowColor = Color.BLACK;
        font24 = generator.generateFont(parameters);
        parameters.size = 48;
        font48 = generator.generateFont(parameters);
        generator.dispose();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
