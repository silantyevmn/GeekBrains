package ru.silantyevmn.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import ru.silantyevmn.game.*;
/**
 * ru.silantyevmn.game
 * Created by Михаил Силантьев on 04.01.2018.
 */
class MenuScreen implements Screen {
    private Texture backgroundTexture;
    private BitmapFont font48;
    private SpriteBatch batch;

    private Stage stage;
    private Skin skin;

    public MenuScreen(SpriteBatch batch) {
        this.batch = batch;
    }

    @Override
    public void show() {
        backgroundTexture = Assets.getInstance().getAssetManager().get("background.png", Texture.class);
        font48 = Assets.getInstance().getAssetManager().get("zorque48.ttf", BitmapFont.class);
        createGUI();
    }

    @Override
    public void render(float delta) {
        update(delta);
        batch.begin();
        batch.draw(backgroundTexture, 0, 0);
        batch.end();
        stage.draw();
    }

    public void createGUI() {
        stage = new Stage(ScreenManager.getInstance().getViewport(), batch);
        Gdx.input.setInputProcessor(stage);
        skin = new Skin();
        skin.addRegions(Assets.getInstance().getAtlas());
        skin.add("font48", font48);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("menuBtn");
        textButtonStyle.font = font48;
        skin.add("simpleBtn", textButtonStyle);

        Button btnNewGame = new TextButton("Start New Game", skin, "simpleBtn");
        Button btnExitGame = new TextButton("Exit Game", skin, "simpleBtn");

        btnNewGame.setPosition(400, 300);
        btnExitGame.setPosition(400, 180);

        stage.addActor(btnNewGame);
        stage.addActor(btnExitGame);

        btnNewGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ScreenManager.getInstance().switchScreen(ScreenManager.ScreenType.GAME);
            }
        });
        btnExitGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
    }

    public void update(float dt) {
        stage.act(dt);
    }

    @Override
    public void resize(int width, int height) {
        ScreenManager.getInstance().onResize(width, height);
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
        backgroundTexture.dispose();
    }
}
