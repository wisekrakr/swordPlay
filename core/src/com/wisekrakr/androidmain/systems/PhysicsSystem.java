package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.wisekrakr.androidmain.PhysicalObjectContactListener;
import com.wisekrakr.androidmain.components.Box2dBodyComponent;
import com.wisekrakr.androidmain.components.TransformComponent;
import com.wisekrakr.androidmain.components.TypeComponent;

import java.util.HashSet;
import java.util.Set;

public class PhysicsSystem extends IteratingSystem {

    private static final float MAX_STEP_TIME = 1/300f;
    private static float accumulator = 0f;

    private World world;
    private Set<Entity> gameObjects;

    private ComponentMapper<Box2dBodyComponent> box2dBodyComponentMapper = ComponentMapper.getFor(Box2dBodyComponent.class);
    private ComponentMapper<TransformComponent> transformComponentMapper = ComponentMapper.getFor(TransformComponent.class);

    @SuppressWarnings("unchecked")
    public PhysicsSystem() {
        super(Family.all(Box2dBodyComponent.class, TransformComponent.class).get());

        world = new World(new Vector2(0,0), true);
        world.setContactListener(new PhysicalObjectContactListener());

        gameObjects = new HashSet<Entity>();
    }

    public World getWorld() {
        return world;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        float frameTime = Math.min(deltaTime, 0.60f);
        accumulator += frameTime;

        if(accumulator >= MAX_STEP_TIME) {
            world.step(MAX_STEP_TIME, 2, 6);
            accumulator -= MAX_STEP_TIME;

            for (Entity entity : gameObjects) {

                TransformComponent transformComponent = transformComponentMapper.get(entity);
                Box2dBodyComponent bodyComponent = box2dBodyComponentMapper.get(entity);

                Vector2 position = bodyComponent.body.getPosition();

                transformComponent.position.x = position.x;
                transformComponent.position.y = position.y;
                transformComponent.rotation = bodyComponent.body.getAngle() * MathUtils.radiansToDegrees;

                if (bodyComponent.isDead){
                    world.destroyBody(bodyComponent.body);
                    getEngine().removeEntity(entity);
                    System.out.println("removed entity: " + entity.getComponent(TypeComponent.class).getType()); //todo remove
                }
            }
        }
        gameObjects.clear();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        gameObjects.add(entity);
    }
}
