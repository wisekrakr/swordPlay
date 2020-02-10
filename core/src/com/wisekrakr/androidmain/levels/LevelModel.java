package com.wisekrakr.androidmain.levels;

import com.badlogic.ashley.core.Entity;
import com.wisekrakr.androidmain.MainGame;
import com.wisekrakr.androidmain.components.*;
import com.wisekrakr.androidmain.components.objects.EnemyComponent;
import com.wisekrakr.androidmain.components.objects.PlayerComponent;
import com.wisekrakr.androidmain.factories.EntityFactory;
import com.wisekrakr.androidmain.GameConstants;


import com.wisekrakr.androidmain.factories.LevelFactory;

import com.wisekrakr.androidmain.helpers.GameHelper;
import com.wisekrakr.androidmain.helpers.PowerHelper;
import com.wisekrakr.androidmain.retainers.ScoreKeeper;
import com.wisekrakr.androidmain.retainers.SelectedCharacter;
import com.wisekrakr.androidmain.systems.PhysicsSystem;

import java.util.Iterator;


public class LevelModel extends AbstractLevelContext{

    private MainGame game;

    private LevelFactory levelFactory;
    private EntityFactory entityFactory;
    private float powerInitTime;
    private Integer enemies;

    public LevelModel(MainGame game) {
        this.game = game;

        entityFactory = new EntityFactory(game, game.getGameThread().getPhysicsSystem().getWorld());
        levelFactory = new LevelFactory(game, entityFactory);

    }

//    4 walls as game objects (todo: closing in after some time?)
    private void createGameBorders(){

        entityFactory.createWalls(0,0, 1f, GameConstants.WORLD_HEIGHT *2);
        entityFactory.createWalls(GameConstants.WORLD_WIDTH,0, 1f, GameConstants.WORLD_HEIGHT *2);
        entityFactory.createWalls(GameConstants.WORLD_WIDTH,GameConstants.WORLD_HEIGHT, GameConstants.WORLD_WIDTH *2,1f);
        entityFactory.createWalls(0,0, GameConstants.WORLD_WIDTH * 2, 1f);
    }

    public void setEnemies(Integer enemies) {
        this.enemies = enemies;
    }

    @Override
    public void startLevel(int numberOfLevel) {
        createGameBorders();
        spawnNewPlayer();
        ScoreKeeper.setInitialEnemies(numberOfLevel);
        setEnemies(0);
        levelFactory.getLevel(LevelNumber.valueOf(numberOfLevel));
    }

    @Override
    public void updateLevel(int numberOfLevel, float delta) {

//       When the player is destroyed but the player still has lives, spawn a new player
        if (SelectedCharacter.isDestroyed() && ScoreKeeper.lives > 0){

            spawnNewPlayer();

        }
//        When the player is in the game
        else if (!SelectedCharacter.isDestroyed()){
            Iterator<Entity>iterator = game.getEngine().getEntities().iterator();

            if(iterator.hasNext()){
                Entity ent = iterator.next();
                TypeComponent.Type type = ent.getComponent(TypeComponent.class).getType();

                if(type == TypeComponent.Type.PLAYER){
                    if (ent.getComponent(PlayerComponent.class).isMoving()) {

                        game.getGameThread().getTimeKeeper().time -= delta;
                        powerUpInitializer();

                        if (game.getGameThread().getTimeKeeper().time <=0){
                            completeLevel(numberOfLevel);
                        }
                    } else {
                        game.getGameThread().getTimeKeeper().setTime(game.getGameThread().getTimeKeeper().time);
                    }
                }
            }

            float sweetSpotX = GameHelper.notFilledPosition(game).x;
            float sweetSpotY = GameHelper.notFilledPosition(game).y;

        //   Make sure the right amount of enemies keeps spawning in
            if(enemies < ScoreKeeper.getInitialEnemies()){
                for (int i = enemies; i < numberOfLevel; i++) {
                    entityFactory.createEnemy(sweetSpotX, sweetSpotY);
                }
                enemies = ScoreKeeper.getInitialEnemies();
                System.out.println("new enemy");
            }
        }
//        When the player has no more lives, it is a game over
        else {
            gameOver(numberOfLevel);
        }
        scoring();
    }

    private void spawnNewPlayer(){
        SelectedCharacter.setDestroyed(false);
        entityFactory.createPlayer(
                GameConstants.WORLD_WIDTH /2, GameConstants.WORLD_HEIGHT /2
        );
    }

    private void scoring(){

        for (Entity entity: game.getEngine().getEntities()){
            if (entity.getComponent(TypeComponent.class).getType() == TypeComponent.Type.ENEMY){
                if (entity.getComponent(CollisionComponent.class).hitSword && entity.getComponent(EnemyComponent.class).isDestroy()){
                    ScoreKeeper.setPointsToGive(25);
                    ScoreKeeper.setScore(ScoreKeeper.getPointsToGive());
                }
            }
            if (entity.getComponent(TypeComponent.class).getType() == TypeComponent.Type.PLAYER){
                if (entity.getComponent(CollisionComponent.class).hitSword && entity.getComponent(com.wisekrakr.androidmain.components.objects.PlayerComponent.class).isDestroy()){
                    ScoreKeeper.setPointsToGive(-50);
                    ScoreKeeper.setScore(ScoreKeeper.getPointsToGive());
                }
            }
        }
    }

    private void powerUpInitializer(){
        if (powerInitTime == 0){
            powerInitTime = game.getGameThread().getTimeKeeper().gameClock;
        }

        if (game.getGameThread().getTimeKeeper().gameClock - powerInitTime > game.getGameThread().getTimeKeeper().powerTime) {
            if (ScoreKeeper.getInitialPowerUps() == 0) {
                entityFactory.createPower(
                        GameHelper.notFilledPosition(game).x,
                        GameHelper.notFilledPosition(game).y,
                        GameHelper.generateRandomNumberBetween(-20f, 20f),
                        GameHelper.generateRandomNumberBetween(-20f, 20f),
                        PowerHelper.randomPowerUp()
                );
                ScoreKeeper.setInitialPowerUps(1);
            }
            powerInitTime = game.getGameThread().getTimeKeeper().gameClock;
        }
    }

    @Override
    public void completeLevel(int numberOfLevel) {
        if (ScoreKeeper.getScore() > game.getGamePreferences().getHighScore()){
            game.getGamePreferences().setHighScore(ScoreKeeper.getScore());
        }

        game.getGamePreferences().setLevelCompleted(numberOfLevel, true);
        cleanUp();
    }

    @Override
    public void gameOver(int numberOfLevel) {
        ScoreKeeper.reset();

        cleanUp();

        game.changeScreen(MainGame.ENDGAME);
    }

    private void cleanUp(){
        for (Entity entity: game.getEngine().getEntities()){
            entity.getComponent(Box2dBodyComponent.class).isDead = true;
        }
        game.getGameThread().getTimeKeeper().reset();
    }
}
