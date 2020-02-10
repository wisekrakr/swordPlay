package com.wisekrakr.androidmain.retainers;

import com.wisekrakr.androidmain.components.EntityStyle;

public class SelectedCharacter {

    private static float swordLength;
    private static float swordGirth;

    public static float getSwordLength() {
        return swordLength;
    }

    public static void setSwordLengthGirth(float swordLength, float swordGirth) {
        SelectedCharacter.swordLength = swordLength;
        SelectedCharacter.swordGirth = swordGirth;
    }

    public static float getSwordGirth() {
        return swordGirth;
    }

    private static EntityStyle style;

    public static void setSelectedCharacter(EntityStyle entityStyle){
        style = entityStyle;
    }

    public static EntityStyle getSelectedCharacterStyle() {
        return style;
    }

    private static boolean destroy;

    public static boolean isDestroyed() {
        return destroy;
    }

    public static void setDestroyed(boolean destroyed) {
        destroy = destroyed;
    }
}
