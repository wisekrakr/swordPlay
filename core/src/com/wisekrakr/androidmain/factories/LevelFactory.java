package com.wisekrakr.androidmain.factories;

import com.wisekrakr.androidmain.MainGame;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.levels.LevelNumber;

public class LevelFactory {

    private static float width = GameConstants.WORLD_WIDTH;
    private static float height = GameConstants.WORLD_HEIGHT;

    private MainGame game;
    private EntityFactory entityFactory;

    public LevelFactory(MainGame game, EntityFactory entityFactory) {
        this.game = game;
        this.entityFactory = entityFactory;
    }

    public void getLevel(LevelNumber levelNumber){

        switch (levelNumber){

            case TWO:
            case FIVE:

                entityFactory.createObstacle(
                        200, 50, -200, 0, 30, 10
                );
                entityFactory.createObstacle(
                        50, height - 50, 200, 0, 30, 10
                );
                break;
            case THREE:
            case SIX:
                entityFactory.createObstacle(
                        200, 50, -200, 0, 30, 10
                );
                entityFactory.createObstacle(
                        50, height - 50, 200, 0, 30, 10
                );
                entityFactory.createObstacle(
                        width/2 - 15, height - 100, 0, 100, 10, 30
                );
                break;

        }
    }
}
