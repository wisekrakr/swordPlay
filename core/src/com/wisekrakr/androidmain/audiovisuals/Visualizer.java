package com.wisekrakr.androidmain.audiovisuals;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.wisekrakr.androidmain.MainGame;
import com.wisekrakr.androidmain.components.*;
import com.wisekrakr.androidmain.components.objects.GameObject;
import com.wisekrakr.androidmain.systems.PhysicsDebugSystem;
import com.wisekrakr.androidmain.systems.RenderingSystem;

public class Visualizer implements Disposable {

    private MainGame game;
    private RenderingSystem renderingSystem;
    private EntityVisuals entityVisuals;

    private final SpriteBatch spriteBatch;

    public Visualizer(MainGame game) {
        this.game = game;

        spriteBatch = new SpriteBatch();

        addSystems();
    }

    private void addSystems(){
        renderingSystem = new RenderingSystem(spriteBatch);

        game.getEngine().addSystem(renderingSystem);
        game.getEngine().addSystem(new PhysicsDebugSystem(game.getGameThread().getPhysicsSystem().getWorld(), renderingSystem.getCamera()));

        entityVisuals = new EntityVisuals(game, spriteBatch);
    }


    public RenderingSystem getRenderingSystem() {
        return renderingSystem;
    }


    public void draw(){

        spriteBatch.setProjectionMatrix(renderingSystem.getCamera().combined);

        spriteBatch.begin();

        for (Entity entity: game.getEngine().getEntities()){
            try {
                entityVisuals.visualizeEntity(entity);
            } catch (Exception e){
                System.out.println(this.getClass() + " " + e);
            }

        }
        spriteBatch.end();
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
    }
}
