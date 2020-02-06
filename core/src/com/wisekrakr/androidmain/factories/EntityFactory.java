package com.wisekrakr.androidmain.factories;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.wisekrakr.androidmain.MainGame;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.PhysicalObjectContactListener;
import com.wisekrakr.androidmain.components.Box2dBodyComponent;

import com.wisekrakr.androidmain.components.EntityStyle;
import com.wisekrakr.androidmain.helpers.ComponentHelper;
import com.wisekrakr.androidmain.helpers.PowerHelper;

import java.util.ArrayList;
import java.util.List;

import static com.wisekrakr.androidmain.components.TypeComponent.Type.*;


/**
 * Class with manually made entities. Connect components to the appropriate entity. (see ComponentHelper)
 */
public class EntityFactory {

    private BodyFactory bodyFactory;
    public World world;
    private MainGame game;
    private PooledEngine engine;
    /**
     * @param game Class that extends Game
     * @param pooledEngine Game engine. All entities are placed in here.
     */
    public EntityFactory(MainGame game, PooledEngine pooledEngine){
        this.game = game;
        this.engine = pooledEngine;

        world = new World(new Vector2(0,0), true);
        world.setContactListener(new PhysicalObjectContactListener());

        bodyFactory = BodyFactory.getBodyFactoryInstance(world);
    }

    /**FOR ALL ENTITIES CREATED BELOW:
     *
     * @param x placement on x-axis
     * @param y placement on y-axis
     * @param velocityX velocity on x-axis
     * @param velocityY velocity on y-axis
     * @param width width of the body
     * @param height height of the body
     * @param material friction/restitution etc.
     * @param bodyType static/dynamic/kinematic
     */

    public void createObstacle(float x, float y, float velocityX, float velocityY, float width, float height, BodyFactory.Material material, BodyDef.BodyType bodyType){
        Entity entity = engine.createEntity();

        Box2dBodyComponent bodyComponent = engine.createComponent(Box2dBodyComponent.class);
        ComponentHelper.getComponentInitializer().textureComponent(engine, entity);
        ComponentHelper.getComponentInitializer().typeComponent(engine, entity, OBSTACLE);
        ComponentHelper.getComponentInitializer().transformComponent(engine, entity, x, y, 0);
        ComponentHelper.getComponentInitializer().collisionComponent(engine, entity);

        bodyComponent.body = bodyFactory.makeBoxPolyBody(x, y, width, height, material, bodyType);
        bodyComponent.body.setLinearVelocity(velocityX, velocityY);

        ComponentHelper.getComponentInitializer().obstacleComponent(
                engine,
                entity,
                x, y,
                velocityX, velocityY,
                width, height
        );



        bodyComponent.body.setUserData(entity);

        entity.add(bodyComponent);

        engine.addEntity(entity);
    }

    public void createEnemy(float x, float y, EntityStyle style, float swordLength, float swordGirth){

        Entity entity = engine.createEntity();

        Box2dBodyComponent bodyComponent = engine.createComponent(Box2dBodyComponent.class);
        ComponentHelper.getComponentInitializer().textureComponent(engine, entity);
        ComponentHelper.getComponentInitializer().typeComponent(engine, entity, ENEMY);
        ComponentHelper.getComponentInitializer().collisionComponent(engine, entity);

        float width = GameConstants.PLAYER_WIDTH;
        float height = GameConstants.PLAYER_HEIGHT;

        bodyComponent.body = bodyFactory.makeBoxPolyBody(x, y, width, height, BodyFactory.Material.RUBBER, BodyDef.BodyType.DynamicBody);

        ComponentHelper.getComponentInitializer().transformComponent(engine, entity, x, y, bodyComponent.body.getAngle());

        bodyComponent.body.setBullet(true);

        bodyComponent.body.setUserData(entity);

        entity.add(bodyComponent);

        List<Entity>entities = new ArrayList<Entity>();

        Entity sword = createSword(
                x, y,
                bodyComponent.body.getLinearVelocity().x, bodyComponent.body.getLinearVelocity().y,
                swordLength,swordGirth,
                bodyComponent.body.getAngle(),
                entity
        );
        entities.add(sword);

        Entity shield = createShield(
                x -  GameConstants.SHIELD_RADIUS,
                y - height/1.5f,
                bodyComponent.body.getLinearVelocity().x, bodyComponent.body.getLinearVelocity().y,
                GameConstants.SHIELD_RADIUS,
                entity
        );

        entities.add(shield);


        ComponentHelper.getComponentInitializer().enemyComponent(engine, entity, x, y, width, height, style, entities, swordLength, swordGirth);

        engine.addEntity(entity);
    }

    public void createPlayer(float x, float y, float width, float height, float swordLength, float swordGirth, EntityStyle style){

        Entity player = engine.createEntity();

        Box2dBodyComponent bodyComponent = engine.createComponent(Box2dBodyComponent.class);
        ComponentHelper.getComponentInitializer().typeComponent(engine, player, PLAYER);
        ComponentHelper.getComponentInitializer().collisionComponent(engine, player);

        bodyComponent.body = bodyFactory.makeBoxPolyBody(x, y, width, height, BodyFactory.Material.WOOD, BodyDef.BodyType.DynamicBody, true);

        ComponentHelper.getComponentInitializer().transformComponent(engine, player, x, y, bodyComponent.body.getAngle());

        bodyComponent.body.setBullet(true);

        bodyComponent.body.setUserData(player);

        player.add(bodyComponent);

        List<Entity>entities = new ArrayList<Entity>();

        Entity sword = createSword(
                x, y,
                bodyComponent.body.getLinearVelocity().x, bodyComponent.body.getLinearVelocity().y,
                swordLength,swordGirth,
                bodyComponent.body.getAngle(),
                player
        );

        entities.add(sword);


        Entity shield = createShield(
                x -  GameConstants.SHIELD_RADIUS,
                y - height/1.5f,
                bodyComponent.body.getLinearVelocity().x, bodyComponent.body.getLinearVelocity().y,
                GameConstants.SHIELD_RADIUS,
                player
        );

        entities.add(shield);

        ComponentHelper.getComponentInitializer().playerComponent(engine, player, x,y, width, height, entities, swordLength, swordGirth, style);

        engine.addEntity(player);

    }

    public void createWalls(float x, float y, float width, float height) {
        Entity entity = engine.createEntity();

        Box2dBodyComponent bodyComponent = engine.createComponent(Box2dBodyComponent.class);
        ComponentHelper.getComponentInitializer().textureComponent(engine, entity);
        ComponentHelper.getComponentInitializer().typeComponent(engine, entity, SCENERY);
        ComponentHelper.getComponentInitializer().collisionComponent(engine, entity);

        bodyComponent.body = bodyFactory.makeBoxPolyBody(x, y, width, height, BodyFactory.Material.STEEL, BodyDef.BodyType.StaticBody);

        ComponentHelper.getComponentInitializer().wallComponent(
                engine,
                entity,
                x,y,
                width, height
        );

        bodyComponent.body.setUserData(entity);

        entity.add(bodyComponent);

        engine.addEntity(entity);

    }

    public void createPower(float x, float y, float velocityX, float velocityY, PowerHelper.Power power) {
        Entity entity = engine.createEntity();

        ComponentHelper.getComponentInitializer().collisionComponent(engine, entity);
        ComponentHelper.getComponentInitializer().typeComponent(engine, entity, POWER);

        Box2dBodyComponent bodyComponent = engine.createComponent(Box2dBodyComponent.class);

        float width = GameConstants.POWER_WIDTH;
        float height = GameConstants.POWER_HEIGHT;

        bodyComponent.body = bodyFactory.makeBoxPolyBody(x, y,width, height, BodyFactory.Material.STONE, BodyDef.BodyType.KinematicBody);

        ComponentHelper.getComponentInitializer().transformComponent(engine, entity, x, y, bodyComponent.body.getAngle());

        ComponentHelper.getComponentInitializer().powerUpComponent(engine, entity, x, y, velocityX, velocityY,  width, height, power);

        bodyComponent.body.setUserData(entity);

        bodyComponent.body.setBullet(true);

        entity.add(bodyComponent);

        engine.addEntity(entity);

    }

    private Entity createSword(float x, float y, float velocityX, float velocityY, float length, float girth, float direction, Entity attachedEntity) {
        Entity entity = engine.createEntity();

        Box2dBodyComponent bodyComponent = engine.createComponent(Box2dBodyComponent.class);
        ComponentHelper.getComponentInitializer().textureComponent(engine, entity);
        ComponentHelper.getComponentInitializer().typeComponent(engine, entity, SWORD);
        ComponentHelper.getComponentInitializer().collisionComponent(engine, entity);
        ComponentHelper.getComponentInitializer().transformComponent(engine,entity,x,y,direction);

        bodyComponent.body = bodyFactory.makeBoxPolyBody(x, y, length, girth, BodyFactory.Material.WOOD, BodyDef.BodyType.DynamicBody);

//        bodyComponent.body.setBullet(true);

        bodyComponent.body.setUserData(entity);

        entity.add(bodyComponent);

        ComponentHelper.getComponentInitializer().swordComponent(
                engine,
                entity,
                attachedEntity,
                velocityX, velocityY,
                length, girth,
                direction
        );

        engine.addEntity(entity);

        //Weld sword to attachedEntity
        WeldJointDef def = new WeldJointDef();
        def.bodyA = attachedEntity.getComponent(Box2dBodyComponent.class).body;
        def.bodyB = bodyComponent.body;
        def.localAnchorA.set(0,-GameConstants.PLAYER_HEIGHT/2);
        def.localAnchorB.set(-GameConstants.PLAYER_WIDTH,0);

        world.createJoint(def);

        return entity;
    }



    private Entity createShield(float x, float y, float velocityX, float velocityY, float radius, Entity attachedEntity) {
        Entity entity = engine.createEntity();

        Box2dBodyComponent bodyComponent = engine.createComponent(Box2dBodyComponent.class);
        ComponentHelper.getComponentInitializer().textureComponent(engine, entity);
        ComponentHelper.getComponentInitializer().typeComponent(engine, entity, SHIELD);
        ComponentHelper.getComponentInitializer().transformComponent(engine,entity,x,y,0);

        bodyComponent.body = bodyFactory.makeCirclePolyBody(x, y, radius, BodyFactory.Material.RUBBER, BodyDef.BodyType.DynamicBody);

        ComponentHelper.getComponentInitializer().shieldComponent(
                engine,
                entity,
                attachedEntity,
                velocityX, velocityY,
                radius
        );

        bodyComponent.body.setUserData(entity);

        entity.add(bodyComponent);

        engine.addEntity(entity);

        RopeJointDef def = new RopeJointDef();
        def.bodyA = attachedEntity.getComponent(Box2dBodyComponent.class).body;
        def.bodyB = bodyComponent.body;
        def.localAnchorA.set(0,-GameConstants.PLAYER_HEIGHT/1.5f);
        def.localAnchorB.set(radius/2, 0);

        world.createJoint(def);

        return entity;
    }


}
