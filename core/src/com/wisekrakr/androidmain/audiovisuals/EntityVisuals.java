package com.wisekrakr.androidmain.audiovisuals;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wisekrakr.androidmain.MainGame;
import com.wisekrakr.androidmain.components.*;
import com.wisekrakr.androidmain.components.objects.EnemyComponent;
import com.wisekrakr.androidmain.components.objects.GameObject;
import com.wisekrakr.androidmain.helpers.PowerHelper;
import com.wisekrakr.androidmain.helpers.SpriteHelper;

public class EntityVisuals implements EntityVisualsContext {

    private MainGame game;
    private SpriteBatch spriteBatch;

    EntityVisuals(MainGame game, SpriteBatch spriteBatch) {
        this.game = game;
        this.spriteBatch = spriteBatch;
    }

    @Override
    public void visualizeEntity(Entity entity) {

        try{
            TypeComponent.Type type = ComponentMapper.getFor(TypeComponent.class).get(entity).getType();
            if (type == TypeComponent.Type.POWER){
                com.wisekrakr.androidmain.components.objects.PowerUpComponent powerUpComponent = game.getGameThread().getComponentMapperSystem().getPowerUpComponentMapper().get(entity);

                switch (PowerHelper.getPower()){

                    case SPEED_BOOST:
                        drawObjectViaAtlas(entity, "images/powerups/powerups.atlas","speedup",
                                powerUpComponent.getWidth(), powerUpComponent.getHeight()
                        );
                        break;
                    case SLOW_MO:
                        drawObjectViaAtlas(entity, "images/powerups/powerups.atlas","slowmo",
                                powerUpComponent.getWidth(), powerUpComponent.getHeight()
                        );
                        break;
                    case EXTRA_LIFE:
                        drawObjectViaAtlas(entity, "images/powerups/powerups.atlas","extralife",
                                powerUpComponent.getWidth(), powerUpComponent.getHeight()
                        );
                        break;
                }

            }else if (type == TypeComponent.Type.OBSTACLE){
                GameObject obstacleComponent = game.getGameThread().getComponentMapperSystem().getObjectComponentMapper().get(entity);

                drawObjectViaAtlas(entity, "images/others/others.atlas", "platform",
                        obstacleComponent.getWidth(), obstacleComponent.getHeight()
                );

            }

            if (type == TypeComponent.Type.ENEMY || type == TypeComponent.Type.PLAYER) {
                EnemyComponent enemyComponent = game.getGameThread().getComponentMapperSystem().getEnemyComponentMapper().get(entity);

                switch (enemyComponent.getEntityStyleContext().getEntityStyle()) {
                    case WHITE_REDHAIR:
                        drawObjectViaAtlas(entity,
                                "images/players/players.atlas", "white_redhair",
                                enemyComponent.getWidth(),
                                enemyComponent.getHeight()
                        );

//                    for (Entity ent: enemyComponent.getAttachedEntities()) {
//                        addSwordAndShield(ent, "sword", "w_shield");
//                    }
                        break;
                    case WHITE_BLONDHAIR:
                        drawObjectViaAtlas(entity,
                                "images/players/players.atlas", "white_blondhair",
                                enemyComponent.getWidth(),
                                enemyComponent.getHeight()
                        );

                        break;
                    case WHITE_BROWNHAIR:
                        drawObjectViaAtlas(entity,
                                "images/players/players.atlas", "white_brownhair",
                                enemyComponent.getWidth(),
                                enemyComponent.getHeight()
                        );

                        break;
                    case BLACK_BLACKHAIR:
                        drawObjectViaAtlas(entity,
                                "images/players/players.atlas", "black_blackhair",
                                enemyComponent.getWidth(),
                                enemyComponent.getHeight()
                        );

                        break;
                    case WHITE_BLACKHAIR:
                        drawObjectViaAtlas(entity,
                                "images/players/players.atlas", "white_blackhair",
                                enemyComponent.getWidth(),
                                enemyComponent.getHeight()
                        );

                        break;

                    case BLACK_BEARD:
                        drawObjectViaAtlas(entity,
                                "images/players/players.atlas", "black_beard",
                                enemyComponent.getWidth(),
                                enemyComponent.getHeight()
                        );

                        break;
                    case BLACK_GREYHAIR:
                        drawObjectViaAtlas(entity,
                                "images/players/players.atlas", "black_greyhair",
                                enemyComponent.getWidth(),
                                enemyComponent.getHeight()
                        );

                        break;
                    case WHITE_BALD:
                        drawObjectViaAtlas(entity,
                                "images/players/players.atlas", "white_bald",
                                enemyComponent.getWidth(),
                                enemyComponent.getHeight()
                        );

                        break;

                }

            }
        }catch (Exception e){
//            System.out.println(this.getClass()  + " " +  e);
        }


    }


    @Override
    public void drawObjectViaAtlas(Entity entity, String atlasPath, String regionPath, float width, float height) {

        if (entity != null){
            SpriteHelper.entitySpriteAtlas(
                    entity,
                    game.assetManager(),
                    atlasPath,
                    regionPath,
                    entity.getComponent(Box2dBodyComponent.class).body,
                    spriteBatch,
                    width, height
            );
        }
    }




}
