package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.wisekrakr.androidmain.components.*;
import com.wisekrakr.androidmain.components.objects.ObstacleComponent;

public class ComponentMapperSystem {

    private ComponentMapper<com.wisekrakr.androidmain.components.objects.EnemyComponent> enemyComponentMapper;
    private ComponentMapper<Box2dBodyComponent> bodyComponentMapper;
    private ComponentMapper<CollisionComponent> collisionComponentMapper;
    private ComponentMapper<com.wisekrakr.androidmain.components.objects.PowerUpComponent> powerUpComponentMapper;
    private ComponentMapper<com.wisekrakr.androidmain.components.objects.ObstacleComponent> obstacleComponentMapper;
    private ComponentMapper<ParticleEffectComponent> particleEffectComponentMapper;
    private ComponentMapper<com.wisekrakr.androidmain.components.objects.PlayerComponent> playerComponentMapper;
    private ComponentMapper<TextureComponent> textureComponentMapper;
    private ComponentMapper<TransformComponent> transformComponentMapper;
    private ComponentMapper<TypeComponent> typeComponentMapper;
    private ComponentMapper<WallComponent> wallComponentMapper;
    private ComponentMapper<com.wisekrakr.androidmain.components.objects.SwordComponent> swordComponentMapper;
    private ComponentMapper<com.wisekrakr.androidmain.components.objects.ShieldComponent> shieldComponentMapper;

    private ComponentMapper<com.wisekrakr.androidmain.components.objects.GameObject> objectComponentMapper;

    public ComponentMapperSystem() {

        enemyComponentMapper = ComponentMapper.getFor(com.wisekrakr.androidmain.components.objects.EnemyComponent.class);
        bodyComponentMapper = ComponentMapper.getFor(Box2dBodyComponent.class);
        collisionComponentMapper = ComponentMapper.getFor(CollisionComponent.class);
        powerUpComponentMapper = ComponentMapper.getFor(com.wisekrakr.androidmain.components.objects.PowerUpComponent.class);
        obstacleComponentMapper = ComponentMapper.getFor(com.wisekrakr.androidmain.components.objects.ObstacleComponent.class);
        particleEffectComponentMapper = ComponentMapper.getFor(ParticleEffectComponent.class);
        playerComponentMapper = ComponentMapper.getFor(com.wisekrakr.androidmain.components.objects.PlayerComponent.class);
        textureComponentMapper = ComponentMapper.getFor(TextureComponent.class);
        transformComponentMapper = ComponentMapper.getFor(TransformComponent.class);
        typeComponentMapper = ComponentMapper.getFor(TypeComponent.class);
        wallComponentMapper = ComponentMapper.getFor(WallComponent.class);
        swordComponentMapper = ComponentMapper.getFor(com.wisekrakr.androidmain.components.objects.SwordComponent.class);
        shieldComponentMapper = ComponentMapper.getFor(com.wisekrakr.androidmain.components.objects.ShieldComponent.class);
        objectComponentMapper = ComponentMapper.getFor(com.wisekrakr.androidmain.components.objects.GameObject.class);
    }

    public ComponentMapper<com.wisekrakr.androidmain.components.objects.EnemyComponent> getEnemyComponentMapper() {
        return enemyComponentMapper;
    }

    public ComponentMapper<Box2dBodyComponent> getBodyComponentMapper() {
        return bodyComponentMapper;
    }

    public ComponentMapper<CollisionComponent> getCollisionComponentMapper() {
        return collisionComponentMapper;
    }

    public ComponentMapper<com.wisekrakr.androidmain.components.objects.PowerUpComponent> getPowerUpComponentMapper() {
        return powerUpComponentMapper;
    }

    public ComponentMapper<ObstacleComponent> getObstacleComponentMapper() {
        return obstacleComponentMapper;
    }

    public ComponentMapper<ParticleEffectComponent> getParticleEffectComponentMapper() {
        return particleEffectComponentMapper;
    }

    public ComponentMapper<com.wisekrakr.androidmain.components.objects.PlayerComponent> getPlayerComponentMapper() {
        return playerComponentMapper;
    }

    public ComponentMapper<TextureComponent> getTextureComponentMapper() {
        return textureComponentMapper;
    }

    public ComponentMapper<TransformComponent> getTransformComponentMapper() {
        return transformComponentMapper;
    }

    public ComponentMapper<TypeComponent> getTypeComponentMapper() {
        return typeComponentMapper;
    }

    public ComponentMapper<WallComponent> getWallComponentMapper() {
        return wallComponentMapper;
    }

    public ComponentMapper<com.wisekrakr.androidmain.components.objects.SwordComponent> getSwordComponentMapper() {
        return swordComponentMapper;
    }

    public ComponentMapper<com.wisekrakr.androidmain.components.objects.ShieldComponent> getShieldComponentMapper() {
        return shieldComponentMapper;
    }
}
