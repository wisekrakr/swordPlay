package com.wisekrakr.androidmain.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.wisekrakr.androidmain.LevelGenerationSystem;
import com.wisekrakr.androidmain.MainGame;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.controls.Controls;
import com.wisekrakr.androidmain.helpers.GameHelper;
import com.wisekrakr.androidmain.systems.*;
import com.wisekrakr.androidmain.audiovisuals.Visualizer;

import java.util.TimerTask;

public class PlayScreen extends ScreenAdapter  {

    private final InputMultiplexer inputMultiplexer;
    private Viewport viewport;

    private Controls controls;

    private MainGame game;
    private LevelGenerationSystem levelGenerationSystem;

    private Visualizer visualizer;
    private InfoDisplay infoDisplay;
    private TouchControl touchControl;

//    private EntityAudio entityAudio;

    public PlayScreen(MainGame game) {
        this.game = game;

        addSystems();

        viewport = new FitViewport(GameConstants.WORLD_WIDTH, GameConstants.WORLD_HEIGHT, visualizer.getRenderingSystem().getCamera());

        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(controls);


        infoDisplay = new InfoDisplay(game);

//        entityAudio = new EntityAudio(game);
    }


    /**
     * Add remaining systems we did not need to add to GameThread.
     */
    private void addSystems() {
        levelGenerationSystem = new LevelGenerationSystem(game);
        levelGenerationSystem.init();

        game.getEngine().addSystem(new PlayerSystem(game));
        game.getEngine().addSystem(new EnemySystem(game));
        game.getEngine().addSystem(new GameObjectSystem(game));

        visualizer = new Visualizer(game);
        controls = new Controls();
        touchControl = new TouchControl(game);

        game.getEngine().addSystem(new PlayerControlSystem(game, controls, visualizer.getRenderingSystem().getCamera()));
        game.getEngine().addSystem(new CollisionSystem(game));

    }

    @Override
    public void show() {

        visualizer.getRenderingSystem().getCamera().setToOrtho(false, GameConstants.WORLD_WIDTH, GameConstants.WORLD_HEIGHT);

        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void render(float delta) {
        /**
         * This gameclock will keep time per second and will be used over the whole game. This delta is holy. (for now)
         *         This screen will therefore keep the time, so when you get a game over or switch from screen and then start a new game,
         *         a new gameclock will start (with every new instance of a PlayScreen.
         */

        game.getGameThread().getTimeKeeper().gameClock += delta;

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getEngine().update(delta);
        visualizer.getRenderingSystem().getCamera().update();

        levelGenerationSystem.updateLevels(delta);


//        visualizer.debugDrawableFilled();
//        visualizer.debugDrawableLine(delta);

        visualizer.draw();

//        entityAudio.audioForAction(controls);
//        entityAudio.audioForEntity();

        infoDisplay.renderDisplay(
                game.getGameThread().getTimeKeeper(),
                delta
        );

    }





    class ColorBackground extends TimerTask{
        @Override
        public void run() {
            Gdx.gl.glClearColor(GameHelper.generateRandomNumberBetween(0, 255),
                    GameHelper.generateRandomNumberBetween(0, 255),
                    GameHelper.generateRandomNumberBetween(0, 255),
                    1
            );
        }
    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void dispose() {
    }


}
