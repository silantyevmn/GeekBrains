package ru.silantyevmn.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ru.silantyevmn.game.RunnerGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height=RunnerGame.HEIGHT;
		config.width=RunnerGame.WIDTH;
		config.title=RunnerGame.TITLE;
		new LwjglApplication(new RunnerGame(), config);
	}
}
