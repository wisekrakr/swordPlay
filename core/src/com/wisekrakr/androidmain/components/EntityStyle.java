package com.wisekrakr.androidmain.components;

public enum EntityStyle {
    WHITE_REDHAIR(8f,3f, 100f),
    WHITE_BLONDHAIR(8f,3f, 100f),
    WHITE_BLACKHAIR(11f,4f, 60f),
    WHITE_BROWNHAIR(9f,5f, 90f),
    BLACK_BLACKHAIR(10f,6f, 70f),
    BLACK_BEARD(14f,5f, 40f),
    BLACK_GREYHAIR(10f,4f, 80f),
    WHITE_BALD(13f,5f, 50f);

    private float swordLength;
    private float swordGirth;
    private float swordSpeed;

    EntityStyle(float length, float girth, float speed) {
        swordLength = length;
        swordGirth = girth;
        swordSpeed = speed;
    }

    public float getSwordLength() {
        return swordLength;
    }

    public float getSwordGirth() {
        return swordGirth;
    }

    public float getSwordSpeed() {
        return swordSpeed;
    }
}
