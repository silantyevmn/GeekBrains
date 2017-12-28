package ru.silantyevmn;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;

/**
 * ru.silantyevmn
 * Created by Михаил Силантьев on 29.12.2017.
 */

public class GameScreen implements Screen {
    private TextureAtlas atlas;
    private SpriteBatch batch;
    private Map map;
    private Hero hero;
    private BitmapFont font24;
    private BitmapFont font96;
    private Trash[] trashes;
    private boolean gameOver;
    private PowerUpsEmitter powerUpsEmitter;
    private int counter;
    private TextureRegion textureAsteroid;

    public GameScreen(SpriteBatch batch){
        this.batch=batch;
    }
    @Override
    public void show() {
        atlas=new TextureAtlas(Gdx.files.internal("mainpack.pack"));
        map = new Map(atlas.findRegion("ground"),atlas.findRegion("star16"));
        map.generateMap();
        hero = new Hero(map,atlas.findRegion("runner"), 200, 300);
        textureAsteroid = atlas.findRegion("asteroid64");
        trashes = new Trash[25];
        for (int i = 0; i < trashes.length; i++) {
            trashes[i] = new Trash(textureAsteroid);
            trashes[i].prepare();
        }
        powerUpsEmitter = new PowerUpsEmitter(atlas.findRegion("money"));
        counter = 0;
        generateFonts();
        gameOver = false;
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
        parameters.size = 96;
        font96 = generator.generateFont(parameters);
        generator.dispose();
    }

    @Override
    public void render(float dt) {
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
            font96.draw(batch, "Game Over", 330, 400);
        }
        batch.end();
    }
    private void restart() {
        gameOver = false;
        map = new Map(atlas.findRegion("ground"),atlas.findRegion("star16"));
        map.generateMap();
        hero = new Hero(map,atlas.findRegion("runner"),200, 300);
        trashes = new Trash[50];
        for (int i = 0; i < trashes.length; i++) {
            trashes[i] = new Trash(textureAsteroid);
            trashes[i].prepare();
        }
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

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        atlas.dispose();
    }
}
