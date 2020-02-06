package com.wisekrakr.androidmain.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.wisekrakr.androidmain.components.objects.GameObject;


public class WallComponent extends GameObject implements Component, Pool.Poolable{


    @Override
    public void reset() {
        this.setPosition(new Vector2());

        this.setWidth(0);
        this.setHeight(0);


        this.setDestroy(false);
    }
}
