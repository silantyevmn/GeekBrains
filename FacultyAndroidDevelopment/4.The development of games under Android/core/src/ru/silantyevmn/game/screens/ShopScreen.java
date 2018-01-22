package ru.silantyevmn.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import ru.silantyevmn.game.*;
/**
 * ru.silantyevmn.game
 * Created by Михаил Силантьев on 04.01.2018.
 */

public class ShopScreen implements Screen {
    private Texture backgroundTexture;
    private TextureRegion[] regions;
    private BitmapFont font32;
    private BitmapFont font48;
    private SpriteBatch batch;

    private Stage stage;
    private Skin skin;

    public ShopScreen(SpriteBatch batch) {
        this.batch = batch;
    }

    @Override
    public void show() {
        backgroundTexture = Assets.getInstance().getAssetManager().get("shopBg.jpg", Texture.class);
        font48 = Assets.getInstance().getAssetManager().get("zorque48.ttf", BitmapFont.class);
        font32 = Assets.getInstance().getAssetManager().get("zorque32.ttf", BitmapFont.class);
        regions = Assets.getInstance().getAtlas().findRegion("shopItems").split(100, 100)[0];
        createGUI();
    }

    @Override
    public void render(float delta) {
        update(delta);
        batch.begin();
        batch.setColor(1,1,1,1);
        batch.draw(backgroundTexture, 0, 0);
        batch.end();
        stage.draw();
    }

    public void createGUI() {
        stage = new Stage(ScreenManager.getInstance().getViewport(), batch);
        Gdx.input.setInputProcessor(stage);
        skin = new Skin();
        skin.addRegions(Assets.getInstance().getAtlas());
        skin.add("font32", font32);

        Label.LabelStyle labelStyle = new Label.LabelStyle(font48, Color.WHITE);
        skin.add("textStyle", labelStyle);
        Label label = new Label("SHOP-RX2", skin, "textStyle");
        label.setPosition(500, 620);

        for (int i = 0; i < 8; i++) {
            TextButton.TextButtonStyle tbs = new TextButton.TextButtonStyle();
            tbs.font = font32;
            tbs.up = new TextureRegionDrawable(regions[i]);
            skin.add("tbs" + i, tbs);
        }
//        Button.ButtonStyle btnStyle = new Button.ButtonStyle();
//        btnStyle.up = skin.getDrawable("btnHeal");
//        skin.add("simpleBtn", btnStyle);
//        skin.add("tbs", tbs);
        int[] values = new int[8];
        for (int i = 0; i < 8; i++) {
            values[i] = MathUtils.random(0, 4);
        }
        Group group = new Group();
        group.setColor(0, 0, 0, 1);
        group.setBounds(0, 0, 400, 400);
        TextButton[][] btnList = new TextButton[8][5];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 5; j++) {
                btnList[i][j] = new TextButton("" + (j + 1), skin, "tbs" + i);
                btnList[i][j].setPosition(90 + 600 * (i / 4) + j * 100, 90 + 120 * (i % 4));
                btnList[i][j].padTop(-60);
                btnList[i][j].padRight(60);
//                btnList[i][j].setRotation(45);
//                btnList[i][j].setTransform(true);
                if(j > values[i]) {
                    btnList[i][j].setColor(0.2f, 0.2f, 0.2f, 1.0f);
                }
                group.addActor(btnList[i][j]);
            }
        }
        group.addActor(label);
        stage.addActor(group);
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
