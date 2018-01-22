package ru.silantyevmn.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ru.silantyevmn.game.screens.ScreenManager;

/**
 * ru.silantyevmn.game
 * Created by Михаил Силантьев on 04.01.2018.
 */

public class RunnerGame extends Game {
    public static final int HEIGHT=720;
    public static final int WIDTH=1280;
    public static final String TITLE="RUNNER GAME";

    private SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        ScreenManager.getInstance().init(this, batch);
        ScreenManager.getInstance().switchScreen(ScreenManager.ScreenType.MENU);
    }

    @Override
    public void render() {
        float dt = Gdx.graphics.getDeltaTime();
        getScreen().render(dt);
    }

    @Override
    public void dispose() {
        batch.dispose();
        ScreenManager.getInstance().dispose();
    }
}
