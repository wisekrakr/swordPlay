package com.wisekrakr.androidmain;


import com.badlogic.gdx.Gdx;

public class GameConstants {

    /*
    Welcome message
     */
    public static String WELCOME_MESSAGE = "a Game, made with Love, by The Wisekrakr";

    /*
    Viewport constants
     */

    public static float WORLD_WIDTH = Gdx.graphics.getWidth()/5f;
    public static float WORLD_HEIGHT = Gdx.graphics.getHeight()/5f;

     /*
    HUD constants
     */

    public static float HUD_WIDTH = Gdx.graphics.getWidth()/10f;
    public static float HUD_HEIGHT = Gdx.graphics.getHeight()/10f;


    /*
   FONT constants
    */
    public static float FONT_SCALE = (Gdx.graphics.getWidth()/100f)/10f;

    /*
    Some constants for game objects
     */

    public static float PLAYER_WIDTH = WORLD_WIDTH /30;
    public static float PLAYER_HEIGHT = WORLD_WIDTH /20;
    public static float POWER_WIDTH = WORLD_WIDTH /60;
    public static float POWER_HEIGHT = WORLD_WIDTH /60;
    public static float TESTICLE_RADIUS = WORLD_WIDTH /90;

    /*
    Constants for speed. Scaled by world size
     */

    public static float PLAYER_SPEED = WORLD_WIDTH/3;
    public static float ENEMY_SPEED = WORLD_WIDTH/3;


}
