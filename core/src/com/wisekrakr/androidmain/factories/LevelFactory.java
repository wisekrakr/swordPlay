package com.wisekrakr.androidmain.factories;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.wisekrakr.androidmain.MainGame;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.helpers.GameHelper;
import com.wisekrakr.androidmain.levels.LevelNumber;
import com.wisekrakr.androidmain.retainers.ScoreKeeper;

public class LevelFactory {

    private static float width = GameConstants.WORLD_WIDTH;
    private static float height = GameConstants.WORLD_HEIGHT;

    private MainGame game;

    public LevelFactory(MainGame game) {
        this.game = game;
    }

    public void getLevel(LevelNumber levelNumber, EntityFactory entityFactory){



        switch (levelNumber){

            case TWO:
            case FIVE:

                game.getGameThread().getEntityFactory().createObstacle(
                        200, 50, -200, 0, 30, 10, BodyFactory.Material.RUBBER, BodyDef.BodyType.KinematicBody
                );
                game.getGameThread().getEntityFactory().createObstacle(
                        50, height - 50, 200, 0, 30, 10, BodyFactory.Material.RUBBER, BodyDef.BodyType.KinematicBody
                );
                break;
            case THREE:
            case SIX:
                game.getGameThread().getEntityFactory().createObstacle(
                        200, 50, -200, 0, 30, 10, BodyFactory.Material.RUBBER, BodyDef.BodyType.KinematicBody
                );
                game.getGameThread().getEntityFactory().createObstacle(
                        50, height - 50, 200, 0, 30, 10, BodyFactory.Material.RUBBER, BodyDef.BodyType.KinematicBody
                );
                game.getGameThread().getEntityFactory().createObstacle(
                        width/2 - 15, height - 100, 0, 100, 10, 30, BodyFactory.Material.RUBBER, BodyDef.BodyType.KinematicBody
                );
                break;

        }
    }
}
