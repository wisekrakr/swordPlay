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

import com.wisekrakr.androidmain.components.objects.EnemyComponent;
import com.wisekrakr.androidmain.components.objects.GameObject;
import com.wisekrakr.androidmain.components.objects.PlayerComponent;
import com.wisekrakr.androidmain.helpers.ComponentHelper;
import com.wisekrakr.androidmain.helpers.EntityStyleHelper;
import com.wisekrakr.androidmain.helpers.PowerHelper;

import java.util.ArrayList;
import java.util.List;

import static com.wisekrakr.androidmain.components.TypeComponent.Type.*;


/**
 * Class with manually made entities. Connect components to the appropriate entity. (see ComponentHelper)
 */
public class EntityFactory {

    private BodyFactory bodyFactory;
    private MainGame game;
    private PooledEngine engine;
    private World world;

    /**
     * @param game Class that extends Game
     * @param world
     *
     */
    public EntityFactory(MainGame game, World world){
        this.game = game;
        this.world = world;
        engine = game.getEngine();


        bodyFactory = BodyFactory.getBodyFactoryInstance(world);
    }

    /**FOR ALL ENTITIES CREATED BELOW:
     *  @param x placement on x-axis
     * @param y placement on y-axis
     * @param velocityX velocity on x-axis
     * @param velocityY velocity on y-axis
     * @param width width of the body
     * @param height height of the body
     */

    public void createObstacle(float x, float y, float velocityX, float velocityY, float width, float height){
        Entity entity = engine.createEntity();

        Box2dBodyComponent bodyComponent = engine.createComponent(Box2dBodyComponent.class);
        ComponentHelper.getComponentInitializer().textureComponent(engine, entity);
        ComponentHelper.getComponentInitializer().typeComponent(engine, entity, OBSTACLE);
        ComponentHelper.getComponentInitializer().transformComponent(engine, entity, x, y, 0);
        ComponentHelper.getComponentInitializer().collisionComponent(engine, entity);

        bodyComponent.body = bodyFactory.makeBoxPolyBody(x, y, width, height, BodyFactory.Material.RUBBER, BodyDef.BodyType.KinematicBody);
        bodyComponent.body.setLinearVelocity(velocityX, velocityY);

        GameObject obstacle = engine.createComponent(GameObject.class);
        obstacle.setPosition(new Vector2(x,y));
        obstacle.setWidth(width);
        obstacle.setHeight(height);

        bodyComponent.body.setUserData(entity);

        entity.add(bodyComponent);

        engine.addEntity(entity);
    }

    public void createEnemy(float x, float y){

        Entity entity = engine.createEntity();

        Box2dBodyComponent bodyComponent = engine.createComponent(Box2dBodyComponent.class);
        ComponentHelper.getComponentInitializer().textureComponent(engine, entity);
        ComponentHelper.getComponentInitializer().typeComponent(engine, entity, ENEMY);
        ComponentHelper.getComponentInitializer().collisionComponent(engine, entity);

        float width = GameConstants.PLAYER_WIDTH;
        float height = GameConstants.PLAYER_HEIGHT;

        // Set up the body component

        bodyComponent.body = bodyFactory.makeBoxPolyBody(x, y, width, height, BodyFactory.Material.RUBBER, BodyDef.BodyType.DynamicBody);

        ComponentHelper.getComponentInitializer().transformComponent(engine, entity, x, y, bodyComponent.body.getAngle());

        bodyComponent.body.setBullet(true);

        bodyComponent.body.setUserData(entity);

        entity.add(bodyComponent);

        // Create an Enemy Component

        EnemyComponent enemyComponent = engine.createComponent(EnemyComponent.class);

        enemyComponent.setPosition(new Vector2(x,y));
        enemyComponent.getEntityStyleContext().setEntityStyle(EntityStyleHelper.randomEntityStyle());
        enemyComponent.setWidth(width);
        enemyComponent.setHeight(height);

        entity.add(enemyComponent);

        // Create a sword and a shield

        List<Entity>entities = new ArrayList<Entity>();

        Entity sword = createSword(
                x, y,
                enemyComponent.getEntityStyleContext().getEntityStyle().getSwordLength(),
                enemyComponent.getEntityStyleContext().getEntityStyle().getSwordGirth(),
                entity
        );
        entities.add(sword);

        Entity shield = createShield(
                x -  GameConstants.SHIELD_RADIUS,
                y - height/1.5f,
                entity
        );

        entities.add(shield);

        enemyComponent.setAttachedEntities(entities);

        engine.addEntity(entity);
    }

    public void createPlayer(float x, float y){

        Entity player = engine.createEntity();

        Box2dBodyComponent bodyComponent = engine.createComponent(Box2dBodyComponent.class);
        ComponentHelper.getComponentInitializer().typeComponent(engine, player, PLAYER);
        ComponentHelper.getComponentInitializer().collisionComponent(engine, player);

        // Set up the body component
        float width = GameConstants.PLAYER_WIDTH;
        float height = GameConstants.PLAYER_HEIGHT;

        bodyComponent.body = bodyFactory.makeBoxPolyBody(x, y, width, height, BodyFactory.Material.WOOD, BodyDef.BodyType.DynamicBody, true);

        ComponentHelper.getComponentInitializer().transformComponent(engine, player, x, y, bodyComponent.body.getAngle());

        bodyComponent.body.setBullet(true);
        bodyComponent.body.setUserData(player);

        player.add(bodyComponent);

        // Set up the Player component

        PlayerComponent playerComponent = engine.createComponent(PlayerComponent.class);

        playerComponent.setPosition(new Vector2(x,y));
        playerComponent.setWidth(width);
        playerComponent.setHeight(height);
        playerComponent.getEntityStyleContext().setEntityStyle(EntityStyleHelper.randomEntityStyle());

        player.add(playerComponent);

        // Attach a sword and shield to the player

        List<Entity>entities = new ArrayList<Entity>();

        Entity sword = createSword(
                x, y,
                playerComponent.getEntityStyleContext().getEntityStyle().getSwordLength(),
                playerComponent.getEntityStyleContext().getEntityStyle().getSwordGirth(),
                player
        );

        entities.add(sword);

        Entity shield = createShield(
                x -  GameConstants.SHIELD_RADIUS,
                y - height/1.5f,
                player
        );

        entities.add(shield);

        playerComponent.setAttachedEntities(entities);

        engine.addEntity(player);

    }

    public void createWalls(float x, float y, float width, float height) {
        Entity entity = engine.createEntity();

        Box2dBodyComponent bodyComponent = engine.createComponent(Box2dBodyComponent.class);
        ComponentHelper.getComponentInitializer().textureComponent(engine, entity);
        ComponentHelper.getComponentInitializer().typeComponent(engine, entity, SCENERY);
        ComponentHelper.getComponentInitializer().collisionComponent(engine, entity);

        bodyComponent.body = bodyFactory.makeBoxPolyBody(x, y, width, height, BodyFactory.Material.STEEL, BodyDef.BodyType.StaticBody);

        GameObject wall = engine.createComponent(GameObject.class);
        wall.setPosition((new Vector2(x,y)));
        wall.setWidth(width);
        wall.setHeight(height);

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

    private Entity createSword(float x, float y, float length, float girth, Entity attachedEntity) {
        Entity entity = engine.createEntity();

        Box2dBodyComponent bodyComponent = engine.createComponent(Box2dBodyComponent.class);
        ComponentHelper.getComponentInitializer().textureComponent(engine, entity);
        ComponentHelper.getComponentInitializer().typeComponent(engine, entity, SWORD);
        ComponentHelper.getComponentInitializer().collisionComponent(engine, entity);
        ComponentHelper.getComponentInitializer().transformComponent(engine,entity,x,y,0);//todo change to direction of attached

        bodyComponent.body = bodyFactory.makeBoxPolyBody(x, y, length, girth, BodyFactory.Material.WOOD, BodyDef.BodyType.DynamicBody);

//        bodyComponent.body.setBullet(true);

        bodyComponent.body.setUserData(entity);

        entity.add(bodyComponent);

        GameObject sword = engine.createComponent(GameObject.class);

        sword.setWidth(length);
        sword.setHeight(girth);
        ArrayList<Entity>list = new ArrayList<Entity>();
        list.add(attachedEntity);
        sword.setAttachedEntities(list);

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

    private Entity createShield(float x, float y, Entity attachedEntity) {
        Entity entity = engine.createEntity();

        Box2dBodyComponent bodyComponent = engine.createComponent(Box2dBodyComponent.class);
        ComponentHelper.getComponentInitializer().textureComponent(engine, entity);
        ComponentHelper.getComponentInitializer().typeComponent(engine, entity, SHIELD);
        ComponentHelper.getComponentInitializer().transformComponent(engine,entity,x,y,0);

        bodyComponent.body = bodyFactory.makeCirclePolyBody(x, y, GameConstants.SHIELD_RADIUS, BodyFactory.Material.RUBBER, BodyDef.BodyType.DynamicBody);

        bodyComponent.body.setUserData(entity);

        entity.add(bodyComponent);

        GameObject shield = engine.createComponent(GameObject.class);

        shield.setPosition(new Vector2(x,y));
        shield.setWidth(GameConstants.SHIELD_RADIUS);
        shield.setHeight(GameConstants.SHIELD_RADIUS);
        ArrayList<Entity>list = new ArrayList<Entity>();
        list.add(attachedEntity);
        shield.setAttachedEntities(list);

        engine.addEntity(entity);

        RopeJointDef def = new RopeJointDef();
        def.bodyA = attachedEntity.getComponent(Box2dBodyComponent.class).body;
        def.bodyB = bodyComponent.body;
        def.localAnchorA.set(0,-GameConstants.PLAYER_HEIGHT/1.5f);
        def.localAnchorB.set(GameConstants.SHIELD_RADIUS/2, 0);

        world.createJoint(def);

        return entity;
    }


}
