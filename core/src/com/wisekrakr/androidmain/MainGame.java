package com.wisekrakr.androidmain;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Game;
import com.wisekrakr.androidmain.screens.*;

import java.util.ArrayList;
import java.util.List;

public class MainGame extends Game {

	private PooledEngine engine;

	private MyAssetManager myAssetManager;
	private GamePreferences gamePreferences;

	private TitleScreen titleScreen;
	private PreferencesScreen preferencesScreen;
	private MenuScreen menuScreen;
	private LevelCompleteScreen levelCompleteScreen;
	private PlayScreen playScreen;
	private EndScreen endScreen;
	private SuperEndScreen superEndScreen;

	public final static int TITLE = 0;
	public final static int MENU = 1;
	public final static int PREFERENCES = 2;
	public final static int LEVELSELECTION = 3;
	public final static int APPLICATION = 4;
	public final static int ENDGAME = 5;
	public final static int SUPERENDGAME = 6;

	private GameThread gameThread;

	@Override
	public void create() {
		start();
	}

	private void start(){
		gamePreferences = new GamePreferences();

		myAssetManager = new MyAssetManager();

		engine = new PooledEngine();

		setScreen(new LoadingScreen(this));

		gameThread = new GameThread(this);

	}

	public void changeScreen(int screen){
		switch(screen){
			case TITLE:
				if(titleScreen == null) titleScreen = new TitleScreen(this);
				this.setScreen(titleScreen);
				break;
			case MENU:
				if(menuScreen == null) menuScreen = new MenuScreen(this);
				this.setScreen(menuScreen);
				break;
			case PREFERENCES:
				if(preferencesScreen == null) preferencesScreen = new PreferencesScreen(this);
				this.setScreen(preferencesScreen);
				break;
			case LEVELSELECTION:
				if (levelCompleteScreen == null) levelCompleteScreen = new LevelCompleteScreen(this);
				this.setScreen(levelCompleteScreen);
				break;
			case APPLICATION:
				if(playScreen == null) playScreen = new PlayScreen(this);
				this.setScreen(new PlayScreen(this));
				break;
			case ENDGAME:
				if(endScreen == null) endScreen = new EndScreen(this);
				this.setScreen(endScreen);
				break;
			case SUPERENDGAME:
				if(superEndScreen == null) superEndScreen = new SuperEndScreen(this);
				this.setScreen(superEndScreen);
				break;
		}
	}



	public GamePreferences getGamePreferences() {
		return gamePreferences;
	}

	public MyAssetManager assetManager() {
		return myAssetManager;
	}

	public PooledEngine getEngine() {
		return engine;
	}

	public GameThread getGameThread() {
		return gameThread;
	}
}
