package ru.silantyevmn.game.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import ru.silantyevmn.game.*;

/**
 * ru.silantyevmn.game
 * Created by Михаил Силантьев on 04.01.2018.
 */

public class ScreenManager {
    public enum ScreenType {
        MENU, GAME, SHOP;
    }

    private RunnerGame rpgGame;
    private Viewport viewport;
    private GameScreen gameScreen;
    private MenuScreen menuScreen;
    private ShopScreen shopScreen;
    private LoadingScreen loadingScreen;
    private Screen targetScreen;

    public static final int VIEW_WIDTH = 1280;
    public static final int VIEW_HEIGHT = 720;

    public RunnerGame getRpgGame() {
        return rpgGame;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public void init(RunnerGame rpgGame, SpriteBatch batch) {
        this.rpgGame = rpgGame;
        this.gameScreen = new GameScreen(batch);
        this.menuScreen = new MenuScreen(batch);
        this.shopScreen = new ShopScreen(batch);
        this.loadingScreen = new LoadingScreen(batch);
        this.viewport = new FitViewport(VIEW_WIDTH, VIEW_HEIGHT);
        this.viewport.apply();
    }

    private static final ScreenManager ourInstance = new ScreenManager();

    public static ScreenManager getInstance() {
        return ourInstance;
    }

    private ScreenManager() {
    }

    public void onResize(int width, int height) {
        viewport.update(width, height, true);
        viewport.apply();
    }

    public void switchScreen(ScreenType type) {
        Screen currentScreen = rpgGame.getScreen();
        Assets.getInstance().clear();
        if (currentScreen != null) {
            currentScreen.dispose();
        }
        rpgGame.setScreen(loadingScreen);
        switch (type) {
            case MENU:
                targetScreen = menuScreen;
                Assets.getInstance().loadAssets(ScreenType.MENU);
                break;
            case GAME:
                targetScreen = gameScreen;
                Assets.getInstance().loadAssets(ScreenType.GAME);
                break;
            case SHOP:
                targetScreen = shopScreen;
                Assets.getInstance().loadAssets(ScreenType.SHOP);
                break;
        }
    }

    public void goToTarget() {
        rpgGame.setScreen(targetScreen);
        targetScreen = null;
    }

    public void dispose() {
        Assets.getInstance().dispose();
        rpgGame.getScreen().dispose();
    }
}
