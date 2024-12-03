package com.example.demo;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;

public class PowerUp extends ActiveActor{

    private static final String IMAGE_NAME = "powerup.png";
    private static final int IMAGE_HEIGHT = 90;
    private static final int VERTICAL_VELOCITY = 2;

    public PowerUp(double initialXPos, double initialYPos, String IMAGE_NAME){
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
    }

    @Override
    public void updatePosition(){
        moveVertically(VERTICAL_VELOCITY);
        //if(getLayoutY() > 600){
           // destroy();
        //}
    }

    //public void updateActor(){
        //updatePosition();
    //}
}
