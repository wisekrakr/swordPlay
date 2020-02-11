package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.wisekrakr.androidmain.components.*;
import com.wisekrakr.androidmain.components.objects.GameObject;

public class ComponentMapperSystem {

    private ComponentMapper<com.wisekrakr.androidmain.components.objects.EnemyComponent> enemyComponentMapper;
    private ComponentMapper<Box2dBodyComponent> bodyComponentMapper;
    private ComponentMapper<CollisionComponent> collisionComponentMapper;
    private ComponentMapper<com.wisekrakr.androidmain.components.objects.PlayerComponent> playerComponentMapper;
    private ComponentMapper<TextureComponent> textureComponentMapper;
    private ComponentMapper<TransformComponent> transformComponentMapper;
    private ComponentMapper<TypeComponent> typeComponentMapper;


    private ComponentMapper<com.wisekrakr.androidmain.components.objects.GameObject> objectComponentMapper;

    public ComponentMapperSystem() {

        enemyComponentMapper = ComponentMapper.getFor(com.wisekrakr.androidmain.components.objects.EnemyComponent.class);
        bodyComponentMapper = ComponentMapper.getFor(Box2dBodyComponent.class);
        collisionComponentMapper = ComponentMapper.getFor(CollisionComponent.class);
        playerComponentMapper = ComponentMapper.getFor(com.wisekrakr.androidmain.components.objects.PlayerComponent.class);
        textureComponentMapper = ComponentMapper.getFor(TextureComponent.class);
        transformComponentMapper = ComponentMapper.getFor(TransformComponent.class);
        typeComponentMapper = ComponentMapper.getFor(TypeComponent.class);
        objectComponentMapper = ComponentMapper.getFor(com.wisekrakr.androidmain.components.objects.GameObject.class);
    }

    public ComponentMapper<GameObject> getObjectComponentMapper() {
        return objectComponentMapper;
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





}
