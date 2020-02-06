package com.wisekrakr.androidmain.components.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;


public class ObstacleComponent extends GameObject implements  Pool.Poolable{



    @Override
    public void reset() {

        this.setPosition(new Vector2());
        this.setVelocityX(0);
        this.setVelocityY(0);

        this.setWidth(0);
        this.setHeight(0);
        this.setDirection(0);

        this.setDestroy(false);
    }
}
