package ru.silantyevmn.game.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import ru.silantyevmn.game.*;
/**
 * ru.silantyevmn.game
 * Created by Михаил Силантьев on 04.01.2018.
 */

public class GameScreen implements Screen {
    private transient SpriteBatch batch;
    private Map map;
    private Hero hero;
    private transient BitmapFont font;
    private PowerUpsEmitter powerUpsEmitter;
    private BulletEmitter bulletEmitter;
    private transient Sound soundTakeMoney;
    private transient Music mainTheme;
    private transient ShapeRenderer shapeRenderer;
    private MonsterEmitter monsterEmitter;
    private final static boolean DEBUG_MODE = true;
    private transient Camera camera;
    private transient Camera screenCamera;
    private TrashEmitter trashEmitter;
    private transient Texture texture;

    private transient Stage stage;
    private transient Skin skin;
    private boolean paused;
    private Rectangle activeRect;

    private boolean isAndroidVersion = true;
    private Button btnLeftMove, btnRightMove, btnJump, btnFire;

    public GameScreen(SpriteBatch batch) {
        this.batch = batch;
    }

    public Map getMap() {
        return map;
    }

    public Hero getHero() {
        return hero;
    }

    public BulletEmitter getBulletEmitter() {
        return bulletEmitter;
    }

    @Override
    public void show() {
        TextureAtlas atlas = Assets.getInstance().getAtlas();
        Gdx.input.setInputProcessor(null);

        map = new Map(350);
        map.generateMap();
        hero = new Hero(this, map, 300, 300);
        monsterEmitter = new MonsterEmitter(this, 20, 0.1f);
        for (int i = 0; i < monsterEmitter.getMonsters().length; i++) {
            monsterEmitter.createMonster(MathUtils.random(0, map.getEndOfWorldX()), 500);
        }
        trashEmitter = new TrashEmitter(this, atlas.findRegion("asteroid64"), 20);
        powerUpsEmitter = new PowerUpsEmitter(atlas.findRegion("powerUps"));
        bulletEmitter = new BulletEmitter(atlas.findRegion("bullet48"), 0);
        afterLoad();
    }

    public void afterLoad() {
        camera = new OrthographicCamera(ScreenManager.VIEW_WIDTH, ScreenManager.VIEW_HEIGHT);
        screenCamera = new OrthographicCamera(ScreenManager.VIEW_WIDTH, ScreenManager.VIEW_HEIGHT);
        screenCamera.position.set(ScreenManager.VIEW_WIDTH / 2, ScreenManager.VIEW_HEIGHT / 2, 0);
        screenCamera.update();
        activeRect = new Rectangle(0, 0, 1280, 720);
        texture = new Texture("bg.jpg");
        mainTheme = Gdx.audio.newMusic(Gdx.files.internal("Jumping bat.wav"));
        mainTheme.setLooping(true);
//        mainTheme.play();
//        soundTakeMoney = Gdx.audio.newSound(Gdx.files.internal("takeMoney.wav"));
        if (DEBUG_MODE) {
            shapeRenderer = new ShapeRenderer();
            shapeRenderer.setAutoShapeType(true);
        }
        Assets.getInstance().loadAssets(ScreenManager.ScreenType.GAME);
        TextureAtlas atlas = Assets.getInstance().getAtlas();
        font = Assets.getInstance().getAssetManager().get("zorque24.ttf", BitmapFont.class);
        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            isAndroidVersion = true;
        }
        if (isAndroidVersion) {
            createGUIforAndroidActions();
        }
    }

    public void createGUIforAndroidActions() {
        stage = new Stage(ScreenManager.getInstance().getViewport(), batch);
        Gdx.input.setInputProcessor(stage);
        skin = new Skin();
        skin.addRegions(Assets.getInstance().getAtlas());
        skin.add("font", font);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("menuBtn");
        textButtonStyle.font = font;
        skin.add("simpleBtn", textButtonStyle);

        btnLeftMove = new TextButton("<", skin, "simpleBtn");
        btnRightMove = new TextButton(">", skin, "simpleBtn");
        btnJump = new TextButton("^", skin, "simpleBtn");
        btnFire = new TextButton("F", skin, "simpleBtn");

        btnLeftMove.setBounds(66,66,64,64);
        btnJump.setBounds(140,140,64,64);
        btnRightMove.setBounds(214,66,64,64);
        btnFire.setBounds(1150,66,64,64);


        stage.addActor(btnLeftMove);
        stage.addActor(btnRightMove);
        stage.addActor(btnJump);
        stage.addActor(btnFire);
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.setProjectionMatrix(screenCamera.combined);
        batch.draw(texture, 0, 0);
        batch.setProjectionMatrix(camera.combined);
        map.render(batch);
        hero.render(batch);
        monsterEmitter.render(batch);
        trashEmitter.render(batch);
        powerUpsEmitter.render(batch);
        bulletEmitter.render(batch);
        batch.setProjectionMatrix(screenCamera.combined);
        hero.renderGUI(batch, font);
        batch.end();
        if (isAndroidVersion) {
            stage.draw();
        }
        if (DEBUG_MODE) {
            shapeRenderer.setProjectionMatrix(camera.combined);
            shapeRenderer.begin();
            shapeRenderer.rect(hero.getHitArea().x, hero.getHitArea().y, hero.getHitArea().width, hero.getHitArea().height);
            shapeRenderer.end();
        }
    }

    public void checkHeroButtons(float dt) {
        if (btnRightMove.isPressed()) {
            hero.moveRight();
        }
        if (btnLeftMove.isPressed()) {
            hero.moveLeft();
        }
        if (btnFire.isPressed()) {
            hero.fire(dt, true);
        }
        if (btnJump.isPressed()) {
            hero.jump();
        }
    }

    public void loadGame() {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(Gdx.files.local("mydata.sav").file()));
            hero = (Hero) ois.readObject();
            map = (Map) ois.readObject();
            monsterEmitter = (MonsterEmitter) ois.readObject();
            hero.afterLoad(this);
            map.afterLoad();
            monsterEmitter.afterLoad(this);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveGame() {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(Gdx.files.local("mydata.sav").file()));
            oos.writeObject(hero);
            oos.writeObject(map);
            oos.writeObject(monsterEmitter);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void update(float dt) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            paused = !paused;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.F8)) {
            saveGame();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.F10)) {
            loadGame();
        }
        if (!paused) {
            map.update(dt);
            hero.setBlock();
            monsterEmitter.setBlocks();
            if (isAndroidVersion) {
                checkHeroButtons(dt);
            }
            hero.update(dt);
            updateHeroCamera();
            monsterEmitter.update(activeRect, dt);
            bulletEmitter.update(dt);
            powerUpsEmitter.update(dt);
            trashEmitter.update(dt);
            checkCollisions();
            bulletEmitter.checkPool();
        }
    }

    public void checkCollisions() {
        for (int i = 0; i < trashEmitter.getTrash().length; i++) {
            if (hero.getHitArea().overlaps(trashEmitter.getTrash()[i].getHitArea())) {
                trashEmitter.recreateTrash(i);
                hero.takeDamage(5);
            }
        }
        for (int i = 0; i < powerUpsEmitter.getPowerUps().length; i++) {
            PowerUp p = powerUpsEmitter.getPowerUps()[i];
            if (p.isActive() && hero.getHitArea().contains(p.getPosition())) {
                p.use(hero);
                p.deactivate();
//                soundTakeMoney.play();
            }
        }
        for (int i = 0; i < bulletEmitter.getActiveList().size(); i++) {
            Bullet b = bulletEmitter.getActiveList().get(i);
            if (!map.checkSpaceIsEmpty(b.getPosition().x, b.getPosition().y)) {
                b.deactivate();
                continue;
            }
            if (b.isPlayersBullet()) {
                for (int j = 0; j < monsterEmitter.getMonsters().length; j++) {
                    Monster m = monsterEmitter.getMonsters()[j];
                    if (m.isActive()) {
                        if (m.getHitArea().contains(b.getPosition())) {
                            b.deactivate();
                            if (m.takeDamage(25)) {
                                powerUpsEmitter.tryToCreatePowerUp(m.getCenterX(), m.getCenterY(), 0.5f);
                                hero.addScore(100);
                            }
                            break;
                        }
                    }
                }
            }
            if (!b.isPlayersBullet()) {
                if (hero.getHitArea().contains(b.getPosition())) {
                    b.deactivate();
                    hero.takeDamage(10);
                    break;
                }
            }
        }

    }

    public void updateHeroCamera() {
        camera.position.set(hero.getCenterX(), hero.getCenterY(), 0);
        if (camera.position.y < ScreenManager.VIEW_HEIGHT / 2) {
            camera.position.y = ScreenManager.VIEW_HEIGHT / 2;
        }
        if (camera.position.x < ScreenManager.VIEW_WIDTH / 2) {
            camera.position.x = ScreenManager.VIEW_WIDTH / 2;
        }
        if (camera.position.x > map.getEndOfWorldX() - ScreenManager.VIEW_WIDTH / 2) {
            camera.position.x = map.getEndOfWorldX() - ScreenManager.VIEW_WIDTH / 2;
        }
        camera.update();
        activeRect.setPosition(camera.position.x - 640, camera.position.y - 360);
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
        mainTheme.dispose();
        if (DEBUG_MODE) {
            shapeRenderer.dispose();
        }
    }
}
